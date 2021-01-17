package pt.rfsfernandes.ui.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

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
    if (mMediaPlayerWalkingMusic != null) {
      mMediaPlayerWalkingMusic.start();
      mMediaPlayerWalkingMusic.setLooping(true);
    }
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
    mMediaPlayerMenuSound.start();
    super.onBackPressed();
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (mMediaPlayerWalkingMusic != null && !mMediaPlayerWalkingMusic.isPlaying()) {
      mMediaPlayerWalkingMusic.start();
    }
  }

  private void initViewModel() {
    mMainViewModel.getPokemonListResponseMutableLiveData().observe(this, pokemonListResponse -> {
      if (pokemonListResponse.size() > 0) {
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
