package pt.rfsfernandes.ui.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import pt.rfsfernandes.MyApplication;
import pt.rfsfernandes.R;
import pt.rfsfernandes.databinding.ActivityMainBinding;
import pt.rfsfernandes.viewmodels.MainViewModel;

public class MainActivity extends FragmentActivity {
  private MainViewModel mMainViewModel;
  private ActivityMainBinding mActivityMainBinding;
  private NavController mNavControllerList;
  private NavController mNavControllerDetails;
  private MediaPlayer mMediaPlayerMenuSound;
  private MediaPlayer mMediaPlayerWalkingMusic;
  private MyApplication mMyApplication;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mMediaPlayerMenuSound = ((MyApplication) getApplication()).getMediaPlayerMenuSound();
    mMediaPlayerWalkingMusic = ((MyApplication) getApplication()).getMediaPlayerWalkingMusic();
    mActivityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());

    View view = mActivityMainBinding.getRoot();
    setContentView(view);
    mMyApplication = (MyApplication) getApplication();
    mMainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

    mNavControllerList = Navigation.findNavController(this, R.id.displayPokemonList);
    mMyApplication.setLandscape(findViewById(R.id.displayPokemonDetails) != null);
    if (mMyApplication.isLandscape()) {
      mNavControllerDetails =
          Navigation.findNavController(this, R.id.displayPokemonDetails);
    }

    initViewModel();

    if (savedInstanceState == null) {
      mMainViewModel.loadResults();
    }

    mMainViewModel.isLoading(true);

    mActivityMainBinding.imageButtonSound.setOnClickListener(e -> {
      handleMusic(true);
    });

  }

  private void handleMusic(boolean fromClick) {

    if (mMyApplication.isCanPlaySounds()) {
      if (fromClick) {
        mMyApplication.setCanPlaySounds(false);
        if (mMediaPlayerWalkingMusic != null && mMediaPlayerWalkingMusic.isPlaying()) {
          mMediaPlayerWalkingMusic.pause();
        }
      } else {
        if (mMediaPlayerWalkingMusic != null && !mMediaPlayerWalkingMusic.isPlaying()) {
          mMediaPlayerWalkingMusic.start();
          mMediaPlayerWalkingMusic.setLooping(true);
        }
      }

    } else {
      if (fromClick) {
        mMyApplication.setCanPlaySounds(true);
        if (mMediaPlayerWalkingMusic != null && !mMediaPlayerWalkingMusic.isPlaying()) {
          mMediaPlayerWalkingMusic.start();
          mMediaPlayerWalkingMusic.setLooping(true);
        }
      } else {
        if (mMediaPlayerWalkingMusic != null && mMediaPlayerWalkingMusic.isPlaying()) {
          mMediaPlayerWalkingMusic.pause();
        }
      }
    }

    handleVolumeButton();

  }

  public void onItemClick(int pokemonId) {
    if (!mMyApplication.isLandscape()) {
      mNavControllerList.navigate(R.id.pokemonDetailsFragment);
    } else {
      if (mActivityMainBinding.linearLaoutDetailsContainer != null) {
        mActivityMainBinding.linearLaoutDetailsContainer.setVisibility(View.VISIBLE);
      }
    }
    mMainViewModel.isLoading(true);
    mMainViewModel.pokemonById(pokemonId);

  }

  @Override
  public void onBackPressed() {
    mMyApplication.playMenuSound();
    super.onBackPressed();
  }

  @Override
  protected void onResume() {
    super.onResume();

    handleMusic(false);
  }

  private void handleVolumeButton() {

    mActivityMainBinding.imageButtonSound.setImageDrawable(
        mMyApplication.isCanPlaySounds() ?
            ResourcesCompat.getDrawable(getResources(), R.drawable.volume_on, getTheme())
            :
            ResourcesCompat.getDrawable(getResources(), R.drawable.volume_off, getTheme())
    );

  }

  private void initViewModel() {
    mMainViewModel.getPokemonListResponseMutableLiveData().observe(this, pokemonListResponse -> {
      if (pokemonListResponse != null && pokemonListResponse.size() > 0) {
        Log.d("Info from db", "Success");
      }
    });

    mMainViewModel.getFecthErrorLiveData().observe(this, message -> {
      Snackbar.make(findViewById(android.R.id.content), message,
          Snackbar.LENGTH_LONG).show();
    });
  }

  @Override
  protected void onPause() {
    super.onPause();
    if (mMediaPlayerWalkingMusic != null && mMediaPlayerWalkingMusic.isPlaying()) {
      mMediaPlayerWalkingMusic.pause();
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mActivityMainBinding = null;
    if (mMediaPlayerWalkingMusic != null && mMediaPlayerWalkingMusic.isPlaying()) {
      mMediaPlayerWalkingMusic.stop();
    }

    if (mMediaPlayerWalkingMusic != null && mMediaPlayerMenuSound.isPlaying()) {
      mMediaPlayerMenuSound.stop();
    }

  }
}
