package ro.itec.waity.table;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by Norbert on 5/13/2016.
 */
public class TableMVP {

   public interface RequiredViewOperations {
      void nfcStatusEvent(boolean status);
      void onNFCDecoded(boolean status, String result);
   }

   public interface RequiredPresenterOperations {
      void checkForNFCStatus();
      void setupForegroundDispatch(Activity activity);
      void stopForegroundDispatch(Activity activity);
      void registerForNFCChangeEvent(Activity activity, boolean register);
      void notifyNFCEvent(boolean status);
      void handleNewIntent(Intent intent);
      void onNFCDecoded(boolean status, String result);
   }

   public interface RequiredModelOperations {
      boolean getNFCStatus();
      void setupForegroundDispatch(Activity activity);
      void stopForegroundDispatch(Activity activity);
      void registerForNFCChangeEvent(Activity activity, boolean register);
      void notifyNFCEvent(boolean newNfcStatus);
      void handleNewIntent(Intent intent);
      void onNFCDecoded(boolean status, String result);
   }
}
