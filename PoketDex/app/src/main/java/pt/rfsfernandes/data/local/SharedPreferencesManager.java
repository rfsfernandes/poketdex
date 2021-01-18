package pt.rfsfernandes.data.local;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;


public class SharedPreferencesManager {
  private static final String SHARED_PREFS = "SHARED_PREFS";
  private static final String SONG_VOLUME = "SONG_VOLUME:KEY";

  private static SharedPreferencesManager instance;
  private static SharedPreferences sharedPrefs;
  private static SharedPreferences.Editor sharedPrefsEditor;

  private static Application mApplication;
  /**
   * Creates the PreferencesManager singleton
   *
   * @return SharedPreferences instance, whether it's a new one or the one that already exists.
   */
  public static SharedPreferencesManager getInstance(Application application) {
    mApplication = application;
    if (instance == null) {
      instance = new SharedPreferencesManager();
    }
    return instance;
  }

  public void setSongState(boolean toPlay){
    if (sharedPrefs == null || sharedPrefsEditor == null) {
      sharedPrefs = mApplication.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
      sharedPrefsEditor = sharedPrefs.edit();

    }
    sharedPrefsEditor.putBoolean(SONG_VOLUME, toPlay);
    sharedPrefsEditor.apply();
  }

  public boolean getSongState() {
    if (sharedPrefs == null) {
      sharedPrefs = mApplication.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
    }
    return sharedPrefs.getBoolean(SONG_VOLUME, true);
  }

}
