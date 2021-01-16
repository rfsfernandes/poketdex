package pt.rfsfernandes.ui.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import pt.rfsfernandes.R;
import pt.rfsfernandes.viewmodels.MainViewModel;

public class MainActivity extends AppCompatActivity {
  private MainViewModel mMainViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mMainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
    initViewModel();

    mMainViewModel.loadResults();
  }

  private void initViewModel() {
    mMainViewModel.getPokemonListResponseMutableLiveData().observe(this, pokemonListResponse -> {
      Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
      Log.d("Info from db", pokemonListResponse.getResultList().get(0).getName().toString());
    });

    mMainViewModel.getFecthErrorLiveData().observe(this, message -> {
      Snackbar.make(findViewById(android.R.id.content), message,
          Snackbar.LENGTH_LONG).show();
    });
  }

}
