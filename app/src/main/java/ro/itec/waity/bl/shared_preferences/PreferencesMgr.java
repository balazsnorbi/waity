package ro.itec.waity.bl.shared_preferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public enum PreferencesMgr {
   INSTANCE;

   private static final String MYPREFS = "WAITY_PREFERENCES";
   private SharedPreferences preferences;
   public final void init(Context context) {
      preferences = context.getSharedPreferences(MYPREFS, Activity.MODE_PRIVATE);
   }

   public String readString(String key) {
      return preferences.getString(key, null);
   }

   public int readInt(String key) {
      return preferences.getInt(key, 0);
   }

   public void writeString(String key, String value) {
      SharedPreferences.Editor editor = preferences.edit();
      editor.putString(key, value);
      editor.commit();
   }

   public void writeInt(String key, int value) {
      SharedPreferences.Editor editor = preferences.edit();
      editor.putInt(key, value);
      editor.commit();
   }
}
