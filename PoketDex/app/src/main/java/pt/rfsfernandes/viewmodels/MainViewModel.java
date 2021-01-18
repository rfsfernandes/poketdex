package pt.rfsfernandes.viewmodels;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import pt.rfsfernandes.data.remote.DataSource;
import pt.rfsfernandes.data.repository.Repository;
import pt.rfsfernandes.data.repository.ResponseCallBack;
import pt.rfsfernandes.model.pokemon.Pokemon;
import pt.rfsfernandes.model.pokemon_species.PokemonSpecies;
import pt.rfsfernandes.model.service_responses.PokemonListResponse;
import pt.rfsfernandes.model.service_responses.PokemonResult;

import static pt.rfsfernandes.custom.Constants.RESULT_LIMIT;


public class MainViewModel extends ViewModel {
  private final Repository mRepository = new Repository(DataSource.getPokemonService());
  private final MutableLiveData<List<PokemonResult>> mPokemonListResponseMutableLiveData =
      new MutableLiveData<>();
  private final MutableLiveData<String> mFecthErrorLiveData = new MutableLiveData<>();

  private final MutableLiveData<Pokemon> mPokemonMutableLiveData = new MutableLiveData<>();

  private final MutableLiveData<Boolean> isLoadingMutableLiveData = new MutableLiveData<>();

  private final MutableLiveData<String> pokemonDescriptionLiveData = new MutableLiveData<>();

  private int currentOffset = 0;

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
    this.mRepository.getPokemonList(currentOffset, RESULT_LIMIT, new ResponseCallBack<PokemonListResponse>() {
      @Override
      public void onSuccess(PokemonListResponse response) {
        for (int i = 0; i < response.getResultList().size(); i++) {
          PokemonResult pokemonResult = response.getResultList().get(i);
          pokemonResult.setListPosition(currentOffset + (i + 1));
        }
        if (getPokemonListResponseMutableLiveData().getValue() != null) {
          List<PokemonResult> tempPokemonList = getPokemonListResponseMutableLiveData().getValue();
          tempPokemonList.remove(tempPokemonList.size() - 1);
          tempPokemonList.addAll(response.getResultList());
          tempPokemonList.add(null);
          getPokemonListResponseMutableLiveData().setValue(tempPokemonList);
        } else {
          response.getResultList().add(null);
          getPokemonListResponseMutableLiveData().setValue(response.getResultList());
        }

        if (response.getNextPage() != null) {
          currentOffset += RESULT_LIMIT;
        }
      }

      @Override
      public void onFailure(String errorMessage) {
        getFecthErrorLiveData().setValue(errorMessage);
      }
    });
  }

  public void pokemonById(int pokemonId) {
    pokemonSpeciesById(pokemonId);
    this.mRepository.getPokemonById(pokemonId, new ResponseCallBack<Pokemon>() {
      @Override
      public void onSuccess(Pokemon response) {
        getPokemonMutableLiveData().setValue(response);
      }

      @Override
      public void onFailure(String errorMessage) {
        getFecthErrorLiveData().setValue(errorMessage);
      }
    });
  }

  public void pokemonSpeciesById(int pokemonId) {
    this.mRepository.getPokemonSpecies(pokemonId, new ResponseCallBack<PokemonSpecies>() {
      @Override
      public void onSuccess(PokemonSpecies response) {
        if (response != null) {
          getPokemonDescriptionLiveData().setValue(response.getFlavourEntriesList().get(0).getFlavourText());
        }
      }

      @Override
      public void onFailure(String errorMessage) {
        getFecthErrorLiveData().setValue(errorMessage);
      }
    });
  }

  public void isLoading(boolean isLoading) {
    getIsLoadingMutableLiveData().setValue(isLoading);
  }

  public void deselectAll() {
    if(getPokemonListResponseMutableLiveData().getValue() != null) {
      List<PokemonResult> pokemonResults = getPokemonListResponseMutableLiveData().getValue();

      if (pokemonResults != null) {
        for (PokemonResult results :
            pokemonResults) {
          if (results != null) {
            results.setSelected(false);
          }
        }
      }
      getPokemonListResponseMutableLiveData().setValue(pokemonResults);
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
    getPokemonListResponseMutableLiveData().setValue(pokemonResults);

  }

}