package pt.rfsfernandes.ui.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import pt.rfsfernandes.MyApplication;
import pt.rfsfernandes.R;
import pt.rfsfernandes.custom.Constants;
import pt.rfsfernandes.custom.dialogs.TypeCustomDialog;
import pt.rfsfernandes.databinding.ActivityMainBinding;
import pt.rfsfernandes.model.type.Type;
import pt.rfsfernandes.viewmodels.MainViewModel;

public class MainActivity extends FragmentActivity implements NavController.OnDestinationChangedListener {
  private MainViewModel mMainViewModel;
  private ActivityMainBinding mActivityMainBinding;
  private NavController mNavControllerList;
  private NavController mNavControllerDetails;
  private MediaPlayer mMediaPlayerMenuSound;
  private MediaPlayer mMediaPlayerWalkingMusic;
  private MyApplication mMyApplication;
  private boolean showTypeInfo;
  private int pokemonId;

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

    mNavControllerList.addOnDestinationChangedListener(this);
    initViewModel(savedInstanceState);

    mMainViewModel.isLoading(true);

    mActivityMainBinding.imageButtonSound.setOnClickListener(e -> {
      handleMusic(true);
    });

    if (mActivityMainBinding.imageButtonBack != null) {
      mActivityMainBinding.imageButtonBack.setVisibility(mMyApplication.isLandscape() ?
          View.INVISIBLE : (pokemonId != 0 ? View.VISIBLE : View.INVISIBLE));

      mActivityMainBinding.imageButtonBack.setOnClickListener(e -> {
        mNavControllerList.popBackStack();
      });
    }

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

    if (!mMyApplication.isLandscape()) {
      if (mNavControllerList.getCurrentDestination() != null && mNavControllerList.getCurrentDestination().getId() == R.id.pokemonDetailsFragment) {
        mMainViewModel.getSelectedPokemonId().postValue(0);
        mNavControllerList.navigate(R.id.pokemonResultListFragment);
      } else {
        super.onBackPressed();
      }
    } else {
      super.onBackPressed();
    }

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

  private void initViewModel(Bundle savedState) {

    mMainViewModel.getPokemonListResponseMutableLiveData().observe(this, pokemonListResponse -> {
      if (pokemonListResponse != null && pokemonListResponse.size() > 0) {
        Log.d("Info from db", "Success");
      }
    });

    mMainViewModel.getFecthErrorLiveData().observe(this, message -> {
      Snackbar.make(findViewById(android.R.id.content), message,
          Snackbar.LENGTH_LONG).show();
    });

    mMainViewModel.getSelectedPokemonId().observe(this, pokemonId -> {
      this.pokemonId = pokemonId;
      if (savedState != null && !savedState.isEmpty()) {
        if (pokemonId != 0) {

          if (!mMyApplication.isLandscape()) {
            mNavControllerList.navigate(R.id.pokemonDetailsFragment);
          } else {
            if (mActivityMainBinding.linearLaoutDetailsContainer != null) {
              mActivityMainBinding.linearLaoutDetailsContainer.setVisibility(View.VISIBLE);
            }
            mNavControllerList.navigate(R.id.pokemonResultListFragment);
          }

        } else {
          mNavControllerList.navigate(R.id.pokemonResultListFragment);
        }
        savedState.clear();
      }
    });

    mMainViewModel.getTypeMutableLiveData().observe(this, type -> {
      mMainViewModel.getSHOW_typeMutableLiveData().observe(this, show_type -> {

        if (showTypeInfo) {
          showTypeInfoDialog(type, show_type);
        }
        showTypeInfo = false;
        mMainViewModel.isLoading(false);
      });
    });

    if (savedState == null) {
      mMainViewModel.loadResults();
    }

  }

  private void showTypeInfoDialog(Type type, Constants.SHOW_TYPE show_type){
    TypeCustomDialog typeCustomDialog = new TypeCustomDialog(this);
    typeCustomDialog.show();

    switch (show_type) {
      case MOVE:
        typeCustomDialog.getPokemonTypesAdapterDoubleDamage().refreshList(type.getDamageRelations().getDoubleDamageTo());
        typeCustomDialog.getPokemonTypesAdapterHalfDamage().refreshList(type.getDamageRelations().getHalfDamageTo());
        typeCustomDialog.getPokemonTypesAdapterNoDamage().refreshList(type.getDamageRelations().getNoDamageTo());
        typeCustomDialog.getTextViewComparisonType().setText("TO");
        break;
      case POKEMON:
        typeCustomDialog.getPokemonTypesAdapterDoubleDamage().refreshList(type.getDamageRelations().getDoubleDamageFrom());
        typeCustomDialog.getPokemonTypesAdapterHalfDamage().refreshList(type.getDamageRelations().getHalfDamageFrom());
        typeCustomDialog.getPokemonTypesAdapterNoDamage().refreshList(type.getDamageRelations().getNoDamageFrom());
        typeCustomDialog.getTextViewComparisonType().setText("FROM");

        break;
    }

    typeCustomDialog.setType(type.getName());
    mMainViewModel.isLoading(false);
  }


  public void setShowTypeInfo(boolean showTypeInfo) {
    this.showTypeInfo = showTypeInfo;
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

  @Override
  public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {


    if (destination.getId() == R.id.pokemonDetailsFragment && !mMyApplication.isLandscape()) {
      if (mActivityMainBinding.imageButtonBack != null) {
        mActivityMainBinding.imageButtonBack.setVisibility(View.VISIBLE);
      }
    } else if (destination.getId() == R.id.pokemonResultListFragment && !mMyApplication.isLandscape()) {
      if (mActivityMainBinding.imageButtonBack != null) {
        mActivityMainBinding.imageButtonBack.setVisibility(View.GONE);
      }
    } else {
      if (mActivityMainBinding.imageButtonBack != null) {
        mActivityMainBinding.imageButtonBack.setVisibility(View.GONE);
      }
    }

  }

}
