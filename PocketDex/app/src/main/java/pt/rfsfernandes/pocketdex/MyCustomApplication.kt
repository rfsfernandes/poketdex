package pt.rfsfernandes.pocketdex

import android.app.Application
import android.media.MediaPlayer
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import pt.rfsfernandes.pocketdex.data.koin.Koin
import pt.rfsfernandes.pocketdex.data.local.SharedPreferencesManager

class MyCustomApplication: Application() {
    var mediaPlayerMenuSound: MediaPlayer? = null
        private set
    var mediaPlayerWalkingMusic: MediaPlayer? = null
        private set
    private var mMediaPlayerPokeball: MediaPlayer? = null
    var isLandscape = false
    var canPlaySounds: Boolean
        get() = SharedPreferencesManager.getInstance(this).songState
        set(canPlaySounds) {
            SharedPreferencesManager.getInstance(this).songState = canPlaySounds
        }

    override fun onCreate() {
        super.onCreate()
        initKoin()
        Fresco.initialize(this)
        try {
            val afd = assets.openFd("menusound.mp3")
            mediaPlayerMenuSound = MediaPlayer()
            mediaPlayerMenuSound!!.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            mediaPlayerMenuSound!!.prepare()
        } catch (e: Exception) {
            mediaPlayerMenuSound = null
            e.printStackTrace()
        }
        try {
            val afd = assets.openFd("walkingtheme.mp3")
            mediaPlayerWalkingMusic = MediaPlayer()
            mediaPlayerWalkingMusic!!.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            mediaPlayerWalkingMusic!!.prepare()
        } catch (e: Exception) {
            mediaPlayerWalkingMusic = null
            e.printStackTrace()
        }
        try {
            val afd = assets.openFd("pokeball.mp3")
            mMediaPlayerPokeball = MediaPlayer()
            mMediaPlayerPokeball!!.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            mMediaPlayerPokeball!!.prepare()
        } catch (e: Exception) {
            mMediaPlayerPokeball = null
            e.printStackTrace()
        }
    }

    private fun initKoin() {
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@MyCustomApplication)
            modules(Koin.modules)
        }
    }

    /**
     * Plays pokemon menu sound
     */
    fun playMenuSound() {
        if (canPlaySounds) {
            if (mediaPlayerMenuSound!!.isPlaying) {
                mediaPlayerMenuSound!!.stop()
            }
            mediaPlayerMenuSound!!.start()
        }
    }

    fun playPokeballSound() {
        if (canPlaySounds) {
            if (mMediaPlayerPokeball!!.isPlaying) {
                mMediaPlayerPokeball!!.stop()
            }
            mMediaPlayerPokeball!!.start()
        }
    }
}