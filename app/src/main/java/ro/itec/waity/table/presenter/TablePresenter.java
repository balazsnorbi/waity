package ro.itec.waity.table.presenter;

import android.app.Activity;
import android.content.Intent;

import ro.itec.waity.table.TableMVP;
import ro.itec.waity.table.model.TableModel;

/**
 * Created by Norbert on 5/14/2016.
 */
public class TablePresenter implements TableMVP.PresenterOperations {

   private final TableMVP.ViewOperations view;
   private final TableMVP.ModelOperations model;

   public TablePresenter(TableMVP.ViewOperations view) {
      this.view = view;
      this.model = new TableModel(this);
   }

   @Override
   public void checkForNFCStatus() {
      forwardNFCEventToView(model.getNFCStatus());
   }

   @Override
   public void setupForegroundDispatch(final Activity activity) {
      model.setupForegroundDispatch(activity);
   }

   @Override
   public void stopForegroundDispatch(final Activity activity) {
      model.stopForegroundDispatch(activity);
   }

   @Override
   public void registerForNFCChangeEvent(final Activity activity, final boolean register) {
      model.registerForNFCChangeEvent(activity, register);
   }

   @Override
   public void notifyNFCEvent(boolean status) {
      forwardNFCEventToView(status);
   }

   @Override
   public void handleNewIntent(Intent intent) {
      model.handleNewIntent(intent);
   }

   @Override
   public void onNFCDecoded(boolean status, String result) {
      view.onNFCDecoded(status, result);
   }

   @Override
   public void saveDeskID(String id) {
      model.saveDeskID(id);
   }

   private void forwardNFCEventToView(boolean nfcStatus) {
      view.nfcStatusEvent(nfcStatus);
   }
}
