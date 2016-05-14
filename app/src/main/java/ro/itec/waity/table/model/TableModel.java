package ro.itec.waity.table.model;

import android.app.Activity;
import android.content.Intent;

import ro.itec.waity.bl.nfc.NFCManager;
import ro.itec.waity.bl.shared_preferences.KeyList;
import ro.itec.waity.bl.shared_preferences.PreferencesMgr;
import ro.itec.waity.table.TableMVP;

/**
 * Created by Norbert on 5/14/2016.
 */
public class TableModel implements TableMVP.ModelOperations {

   private final TableMVP.PresenterOperations presenter;

   public TableModel(TableMVP.PresenterOperations presenterOperations) {
      NFCManager.INSTANCE.setModel(this);
      this.presenter = presenterOperations;
   }

   @Override
   public boolean getNFCStatus() {
      return NFCManager.INSTANCE.isNFCEnabled();
   }

   @Override
   public void setupForegroundDispatch(final Activity activity) {
      NFCManager.INSTANCE.setupForegroundDispatch(activity);
   }

   @Override
   public void stopForegroundDispatch(final Activity activity) {
      NFCManager.INSTANCE.stopForegroundDispatch(activity);
   }

   @Override
   public void registerForNFCChangeEvent(Activity activity, boolean register) {
      NFCManager.INSTANCE.registerForNFCChangeEvent(activity, register);
   }

   @Override
   public void notifyNFCEvent(boolean newNfcStatus) {
      presenter.notifyNFCEvent(newNfcStatus);
   }

   @Override
   public void handleNewIntent(Intent intent) {
      NFCManager.INSTANCE.handleIntent(intent);
   }

   @Override
   public void onNFCDecoded(boolean status, String result) {
      presenter.onNFCDecoded(status, result);
   }

   @Override
   public void saveDeskID(String id) {
      PreferencesMgr.INSTANCE.writeString(KeyList.KEY_DESK_ID, id);
   }
}
