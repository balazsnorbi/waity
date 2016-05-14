package ro.itec.waity.bl.nfc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import ro.itec.waity.R;
import ro.itec.waity.bl.nfc.NFCEventListener;
import ro.itec.waity.bl.nfc.NdefReaderTask;

/**
 * Handles NFC related events
 */
public enum NFCManager {
   INSTANCE;

   private static final String MIME_TEXT_PLAIN = "text/plain";
   private static final String TAG = "NFCManager";
   private NfcAdapter adapter;

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

   /**
    * Used to handle NDEF_DISCOVERED intent.
    *
    * @param listener NFCEventListener
    * @param intent   Intent
    * @return Returns true if the correct event occurred - false if it was ignored.
    */
   public final boolean handleIntent(NFCEventListener listener, Intent intent) {
      boolean handled = false;
      String action = intent.getAction();

      if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {

         String type = intent.getType();
         if (MIME_TEXT_PLAIN.equals(type)) {

            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            new NdefReaderTask(listener).execute(tag);

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
    * Primary check for NFC status: at this point, the user will be able to enable the NFC without manually accessing the settings menu
    * @param context Context
    * @return nfcStatus
    */
   public final boolean checkNFCStatus(final Context context) {
      boolean nfcIsEnabled = adapter.isEnabled();
      if (!nfcIsEnabled) {
         AlertDialog.Builder alertBox = new AlertDialog.Builder(context);
         alertBox.setCancelable(false);
         alertBox.setTitle(R.string.nfc_alert_box_info);
         alertBox.setMessage(R.string.nfc_alert_box_question);
         alertBox.setPositiveButton(R.string.nfc_alert_box_positive_btn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                  Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
                  context.startActivity(intent);
               } else {
                  Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                  context.startActivity(intent);
               }
            }
         });
         alertBox.setNegativeButton(R.string.nfc_alert_box_negative_btn, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
         });
         alertBox.show();
      }

      return nfcIsEnabled;
   }

   /**
    * Getter method to check NFC status
    * @return nfcStatus
    */
   public final boolean isNFCEnabled() {
      return adapter.isEnabled();
   }
}
