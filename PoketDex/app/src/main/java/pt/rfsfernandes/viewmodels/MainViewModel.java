package pt.rfsfernandes.viewmodels;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import pt.rfsfernandes.data.local.AppDatabase;
import pt.rfsfernandes.data.remote.DataSource;
import pt.rfsfernandes.data.repository.Repository;
import pt.rfsfernandes.data.repository.ResponseCallBack;
import pt.rfsfernandes.model.pokemon.Pokemon;
import pt.rfsfernandes.model.pokemon_species.PokemonSpecies;
import pt.rfsfernandes.model.service_responses.PokemonListResponse;
import pt.rfsfernandes.model.service_responses.PokemonResult;

import static pt.rfsfernandes.custom.Constants.RESULT_LIMIT;


public class MainViewModel extends AndroidViewModel {
  private final Repository mRepository;
  private final MutableLiveData<List<PokemonResult>> mPokemonListResponseMutableLiveData =
      new MutableLiveData<>();
  private final MutableLiveData<String> mFecthErrorLiveData = new MutableLiveData<>();

  private final MutableLiveData<Pokemon> mPokemonMutableLiveData = new MutableLiveData<>();

  private final MutableLiveData<Boolean> isLoadingMutableLiveData = new MutableLiveData<>();

  private final MutableLiveData<String> pokemonDescriptionLiveData = new MutableLiveData<>();

  private int currentOffset = 0;

  public MainViewModel(@NonNull Application application) {
    super(application);

    mRepository = new Repository(DataSource.getPokemonService(),
        AppDatabase.getInstance(application).getPokemonDAO());

  }

  public MutableLiveData<List<PokemonResult>> getPokemonListResponseMutableLiveData() {
    return mPokemonListResponseMutableLiveData;
  }

  public MutableLiveData<String> getFecthErrorLiveData() {
    return mFecthErrorLiveData;
  }

  public MutableLiveData<Pokemon> getPokemonMutableLiveData() {
    return mPokemonMutableLiveData;
  }

  public MutableLiveData<Boolean> getIsLoadingMutableLiveData() {
    return isLoadingMutableLiveData;
  }

  public MutableLiveData<String> getPokemonDescriptionLiveData() {
    return pokemonDescriptionLiveData;
  }

  public void loadResults() {
    this.mRepository.getPokemonList(currentOffset, RESULT_LIMIT, new ResponseCallBack<List<PokemonResult>>() {
      @Override
      public void onSuccess(List<PokemonResult> response) {

        if (getPokemonListResponseMutableLiveData().getValue() != null) {
          List<PokemonResult> tempPokemonList = getPokemonListResponseMutableLiveData().getValue();
          tempPokemonList.remove(tempPokemonList.size() - 1);
          tempPokemonList.addAll(response);
          tempPokemonList.add(null);
          getPokemonListResponseMutableLiveData().postValue(tempPokemonList);
        } else {
          response.add(null);
          getPokemonListResponseMutableLiveData().postValue(response);
        }

        if (response.size() > 0) {
          currentOffset += RESULT_LIMIT;
        }
      }

      @Override
      public void onFailure(String errorMessage) {
        getFecthErrorLiveData().postValue(errorMessage);
      }
    });
  }

  public void pokemonById(int pokemonId) {
    pokemonSpeciesById(pokemonId);
    this.mRepository.getPokemonById(pokemonId, new ResponseCallBack<Pokemon>() {
      @Override
      public void onSuccess(Pokemon response) {
        getPokemonMutableLiveData().postValue(response);
      }

      @Override
      public void onFailure(String errorMessage) {
        getFecthErrorLiveData().postValue(errorMessage);
      }
    });
  }

  public void pokemonSpeciesById(int pokemonId) {
    this.mRepository.getPokemonSpecies(pokemonId, new ResponseCallBack<PokemonSpecies>() {
      @Override
      public void onSuccess(PokemonSpecies response) {
        if (response != null) {
          getPokemonDescriptionLiveData().postValue(response.getFlavourEntriesList().get(0).getFlavourText());
        }
      }

      @Override
      public void onFailure(String errorMessage) {
        getFecthErrorLiveData().postValue(errorMessage);
      }
    });
  }

  public void isLoading(boolean isLoading) {
    getIsLoadingMutableLiveData().postValue(isLoading);
  }

  public void deselectAll() {
    if (getPokemonListResponseMutableLiveData().getValue() != null) {
      List<PokemonResult> pokemonResults = getPokemonListResponseMutableLiveData().getValue();

      if (pokemonResults != null) {
        for (PokemonResult results :
            pokemonResults) {
          if (results != null) {
            results.setSelected(false);
          }
        }
      }
      getPokemonListResponseMutableLiveData().postValue(pokemonResults);
    }

  }

  public void setSelected(int listId) {
    List<PokemonResult> pokemonResults = getPokemonListResponseMutableLiveData().getValue();

    if (pokemonResults != null) {
      for (PokemonResult results :
          pokemonResults) {
        if (results != null) {
          results.setSelected(results.getListPosition() == listId);
        }
      }
    }
    getPokemonListResponseMutableLiveData().postValue(pokemonResults);

  }

}