package ro.itec.waity.app;

import com.orm.SugarApp;

import ro.itec.waity.bl.nfc.NFCManager;
import ro.itec.waity.bl.shared_preferences.PreferencesMgr;

/**
 * Created by Norbert on 5/13/2016.
 */
public final class WaityApplication extends SugarApp{

   @Override
   public void onCreate() {
      super.onCreate();

      // Initialise the NFC manager to make the application ready to read NFC tags
      NFCManager.INSTANCE.init(this);

      // Initialise the PreferenceManager to handle UseCases where some date need to be saved
      PreferencesMgr.INSTANCE.init(this);
   }
}
