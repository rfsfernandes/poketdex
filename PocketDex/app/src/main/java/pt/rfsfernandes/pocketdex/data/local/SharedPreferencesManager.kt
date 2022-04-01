package pt.rfsfernandes.pocketdex.data.local

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager {
    var songState: Boolean
        get() {
            return sharedPrefs.getBoolean(SONG_VOLUME, true)
        }
        set(toPlay) {
            sharedPrefsEditor.putBoolean(SONG_VOLUME, toPlay)
            sharedPrefsEditor.apply()
        }

    companion object {
        private const val SHARED_PREFS = "SHARED_PREFS"
        private const val SONG_VOLUME = "SONG_VOLUME:KEY"
        private var instance: SharedPreferencesManager? = null
        private lateinit var sharedPrefs: SharedPreferences
        private lateinit var sharedPrefsEditor: SharedPreferences.Editor
        private var mApplication: Application? = null

        /**
         * Creates the PreferencesManager singleton
         *
         * @return SharedPreferences instance, whether it's a new one or the one that already exists.
         */
        fun getInstance(application: Application?): SharedPreferencesManager {
            mApplication = application
            if (instance == null) {
                sharedPrefs = mApplication!!.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
                sharedPrefsEditor = sharedPrefs.edit()
                instance = SharedPreferencesManager()
            }
            return instance!!
        }
    }
}