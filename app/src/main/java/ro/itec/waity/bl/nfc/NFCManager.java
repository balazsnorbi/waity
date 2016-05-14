package ro.itec.waity.bl.nfc;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.util.Log;

import ro.itec.waity.table.TableMVP;

/**
 * Handles NFC related events
 */
public enum NFCManager implements NFCEventListener{
   INSTANCE {
      @Override
      public void onNFCReadCompleted(boolean success, String nfcString) {
         model.onNFCDecoded(success, nfcString);
      }
   };

   private static final String MIME_TEXT_PLAIN = "text/plain";
   private static final String TAG = "NFCManager";
   private static NfcAdapter adapter;
   private static TableMVP.ModelOperations model;
   private final BroadcastReceiver nfcBroadcastReceiver = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
         final String action = intent.getAction();
         boolean nfcState = false;
         if (action.equals(NfcAdapter.ACTION_ADAPTER_STATE_CHANGED)) {
            final int state = intent.getIntExtra(NfcAdapter.EXTRA_ADAPTER_STATE,
                    NfcAdapter.STATE_OFF);
            switch (state) {
               case NfcAdapter.STATE_OFF:
                  nfcState = false;
                  break;
               case NfcAdapter.STATE_TURNING_OFF:
                  nfcState = false;
                  break;
               case NfcAdapter.STATE_ON:
                  nfcState = true;
                  break;
               case NfcAdapter.STATE_TURNING_ON:
                  nfcState = true;
                  break;
            }
         }
         handleNFCStateChange(nfcState);
      }
   };


   /**
    * Call this method first to initialise the NFCManager
    *
    * @param context Context
    * @return true if NFC is available on this device
    */
   public final boolean init(Context context) {
      boolean succeeded = true;

      adapter = NfcAdapter.getDefaultAdapter(context);

      if (adapter == null) {
         succeeded = false;
      }

      return succeeded;
   }

   public final void setModel(TableMVP.ModelOperations modelOperations) {
      model = modelOperations;
   }

   public final void registerForNFCChangeEvent(Activity activity, boolean register) {
      if (register) {
         IntentFilter filter = new IntentFilter(NfcAdapter.ACTION_ADAPTER_STATE_CHANGED);
         activity.registerReceiver(nfcBroadcastReceiver, filter);
      } else {
         activity.unregisterReceiver(nfcBroadcastReceiver);
      }
   }

   /**
    * Used to handle NDEF_DISCOVERED intent.
    *
    * @param intent   Intent
    * @return Returns true if the correct event occurred - false if it was ignored.
    */
   public final boolean handleIntent(Intent intent) {
      boolean handled = false;
      String action = intent.getAction();

      if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {

         String type = intent.getType();
         if (MIME_TEXT_PLAIN.equals(type)) {

            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            new NdefReaderTask(this).execute(tag);

         } else {
            Log.d(TAG, "Wrong mime type: " + type);
         }
         handled = true;
      }

      return handled;
   }

   /**
    * @param activity The corresponding {@link Activity} requesting the foreground dispatch.
    */
   public final void setupForegroundDispatch(final Activity activity) {
      final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
      intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

      final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);

      IntentFilter[] filters = new IntentFilter[1];
      String[][] techList = new String[][]{};

      // Notice that this is the same filter as in our manifest.
      filters[0] = new IntentFilter();
      filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
      filters[0].addCategory(Intent.CATEGORY_DEFAULT);
      try {
         filters[0].addDataType(MIME_TEXT_PLAIN);
      } catch (IntentFilter.MalformedMimeTypeException e) {
         throw new RuntimeException("Check your mime type.");
      }

      adapter.enableForegroundDispatch(activity, pendingIntent, filters, techList);
   }

   /**
    * @param activity The corresponding activity requesting to stop the foreground dispatch.
    */
   public final void stopForegroundDispatch(final Activity activity) {
      adapter.disableForegroundDispatch(activity);
   }

   /**
    * Getter method to check NFC status
    * @return nfcStatus
    */
   public final boolean isNFCEnabled() {
      return adapter.isEnabled();
   }


   private void handleNFCStateChange(boolean newState) {
      if (model != null) {
         model.notifyNFCEvent(newState);
      }
   }
}
