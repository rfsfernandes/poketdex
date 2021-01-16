package pt.rfsfernandes.ui.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import pt.rfsfernandes.R;
import pt.rfsfernandes.databinding.ActivityMainBinding;
import pt.rfsfernandes.ui.fragments.PokemonDetailsFragment;
import pt.rfsfernandes.ui.fragments.PokemonResultListFragment;
import pt.rfsfernandes.viewmodels.MainViewModel;

public class MainActivity extends FragmentActivity {
  private MainViewModel mMainViewModel;
  private ActivityMainBinding mActivityMainBinding;
  private NavController mNavControllerList;
  private NavController mNavControllerDetails;
  private boolean isLandscape;

  public ActivityMainBinding getActivityMainBinding() {
    return mActivityMainBinding;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mActivityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
    View view = mActivityMainBinding.getRoot();
    setContentView(view);

    mMainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

    mNavControllerList = Navigation.findNavController(this, R.id.displayPokemonList);
    isLandscape = findViewById(R.id.displayPokemonDetails) != null;
    if (isLandscape) {
      mNavControllerDetails =
          Navigation.findNavController(this, R.id.displayPokemonDetails);
    }

    initViewModel();
    mMainViewModel.loadResults();

  }

  public void onItemClick(int pokemonId) {
    if(!isLandscape) {
      mNavControllerList.navigate(R.id.pokemonDetailsFragment);
    }

    mMainViewModel.pokemonById(pokemonId);

  }

  private void initViewModel() {
    mMainViewModel.getPokemonListResponseMutableLiveData().observe(this, pokemonListResponse -> {
      if (pokemonListResponse.size() > 0) {
        Log.d("Info from db", pokemonListResponse.get(0).getName());
      }
    });

    mMainViewModel.getFecthErrorLiveData().observe(this, message -> {
      Snackbar.make(findViewById(android.R.id.content), message,
          Snackbar.LENGTH_LONG).show();
    });
  }


  @Override
  protected void onDestroy() {
    super.onDestroy();

    mActivityMainBinding = null;
  }
}
