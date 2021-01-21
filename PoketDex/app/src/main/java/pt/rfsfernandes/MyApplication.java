package pt.rfsfernandes;

import android.app.Application;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import com.facebook.drawee.backends.pipeline.Fresco;

import pt.rfsfernandes.data.local.SharedPreferencesManager;

/**
 * Class MyApplication created at 1/16/21 19:36 for the project PoketDex
 * By: rodrigofernandes
 */
public class MyApplication extends Application {
  private MediaPlayer mMediaPlayerMenuSound;
  private MediaPlayer mMediaPlayerWalkingMusic;
  private MediaPlayer mMediaPlayerPokeball;
  private boolean isLandscape;
  private boolean canPlaySounds;

  public MediaPlayer getMediaPlayerMenuSound() {
    return mMediaPlayerMenuSound;
  }

  public MediaPlayer getMediaPlayerWalkingMusic() {
    return mMediaPlayerWalkingMusic;
  }

  public boolean isLandscape() {
    return isLandscape;
  }

  public void setLandscape(boolean landscape) {
    isLandscape = landscape;
  }

  public boolean isCanPlaySounds() {
    return SharedPreferencesManager.getInstance(this).getSongState();
  }

  public void setCanPlaySounds(boolean canPlaySounds) {
    SharedPreferencesManager.getInstance(this).setSongState(canPlaySounds);
  }

  @Override
  public void onCreate() {
    super.onCreate();
    Fresco.initialize(this);

    try {
      AssetFileDescriptor afd = getAssets().openFd("menusound.mp3");
      mMediaPlayerMenuSound = new MediaPlayer();
      mMediaPlayerMenuSound.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
      mMediaPlayerMenuSound.prepare();
    } catch (Exception e) {
      mMediaPlayerMenuSound = null;
      e.printStackTrace();
    }

    try {
      AssetFileDescriptor afd = getAssets().openFd("walkingtheme.mp3");
      mMediaPlayerWalkingMusic = new MediaPlayer();
      mMediaPlayerWalkingMusic.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
      mMediaPlayerWalkingMusic.prepare();
    } catch (Exception e) {
      mMediaPlayerWalkingMusic = null;
      e.printStackTrace();
    }

    try {
      AssetFileDescriptor afd = getAssets().openFd("pokeball.mp3");
      mMediaPlayerPokeball = new MediaPlayer();
      mMediaPlayerPokeball.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
      mMediaPlayerPokeball.prepare();

    } catch (Exception e) {
      mMediaPlayerPokeball = null;
      e.printStackTrace();
    }

  }

  /**
   * Plays pokemon menu sound
   */
  public void playMenuSound() {
    if (isCanPlaySounds()) {

      if (mMediaPlayerMenuSound.isPlaying()) {
        mMediaPlayerMenuSound.stop();
      }

      mMediaPlayerMenuSound.start();
    }
  }

  public void playPokeballSound(){
    if (isCanPlaySounds()) {

      if (mMediaPlayerPokeball.isPlaying()) {
        mMediaPlayerPokeball.stop();
      }

      mMediaPlayerPokeball.start();
    }
  }

}
