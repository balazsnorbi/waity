package ro.itec.waity.app;

import android.app.Application;

import ro.itec.waity.bl.nfc.NFCManager;

/**
 * Created by Norbert on 5/13/2016.
 */
public final class WaityApplication extends Application{

   @Override
   public void onCreate() {
      super.onCreate();

      // Initialise the NFC manager to make the application ready to read NFC tags
      NFCManager.INSTANCE.init(this);
   }
}
