package pt.rfsfernandes.viewmodels;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import pt.rfsfernandes.R;
import pt.rfsfernandes.custom.Constants;
import pt.rfsfernandes.custom.callbacks.ResponseCallBack;
import pt.rfsfernandes.data.local.AppDatabase;
import pt.rfsfernandes.data.remote.DataSource;
import pt.rfsfernandes.data.repository.Repository;
import pt.rfsfernandes.model.moves.Moves;
import pt.rfsfernandes.model.pokemon.Pokemon;
import pt.rfsfernandes.model.pokemon.moves.PokemonMoves;
import pt.rfsfernandes.model.pokemon_species.FlavourEntries;
import pt.rfsfernandes.model.pokemon_species.PokemonSpecies;
import pt.rfsfernandes.model.service_responses.PokemonResult;
import pt.rfsfernandes.model.type.Type;

import static pt.rfsfernandes.custom.Constants.RESULT_LIMIT;


public class MainViewModel extends AndroidViewModel {
  private final Repository mRepository;
  private final MutableLiveData<List<PokemonResult>> mPokemonListResponseMutableLiveData =
      new MutableLiveData<>();
  private final MutableLiveData<String> mFecthErrorLiveData = new MutableLiveData<>();

  private final MutableLiveData<Pokemon> mPokemonMutableLiveData = new MutableLiveData<>();

  private final MutableLiveData<Boolean> isLoadingMutableLiveData = new MutableLiveData<>();

  private final MutableLiveData<String> pokemonDescriptionLiveData = new MutableLiveData<>();

  private final MutableLiveData<Integer> detailsPagerLiveData = new MutableLiveData<>();

  private final MutableLiveData<String> detailsTitleLiveData = new MutableLiveData<>();

  private final MutableLiveData<Integer> selectedPokemonId = new MutableLiveData<>();

  private final MutableLiveData<List<Moves>> movesInfoLiveData = new MutableLiveData<>();

  private final MutableLiveData<Type> mTypeMutableLiveData = new MutableLiveData<>();

  private final MutableLiveData<Constants.SHOW_TYPE> mSHOW_typeMutableLiveData = new MutableLiveData<>();

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

  public MutableLiveData<Integer> getDetailsPagerLiveData() {
    return detailsPagerLiveData;
  }

  public MutableLiveData<String> getDetailsTitleLiveData() {
    return detailsTitleLiveData;
  }

  public MutableLiveData<Integer> getSelectedPokemonId() {
    return selectedPokemonId;
  }

  public MutableLiveData<List<Moves>> getMovesInfo() {
    return movesInfoLiveData;
  }

  public MutableLiveData<Type> getTypeMutableLiveData() {
    return mTypeMutableLiveData;
  }

  public MutableLiveData<Constants.SHOW_TYPE> getSHOW_typeMutableLiveData() {
    return mSHOW_typeMutableLiveData;
  }

  /**
   * Fetches a list of Pokemons with an offset and a limit
   */
  public void getPokemonList() {
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

  /**
   * Fetches a Pokemon by it's ID
   *
   * @param pokemonId Pokemon ID
   */
  public void pokemonById(int pokemonId) {
    getSelectedPokemonId().postValue(pokemonId);
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

  /**
   * Fetches a Species of a Pokemon by it's ID
   *
   * @param pokemonId Pokemon ID
   */
  public void pokemonSpeciesById(int pokemonId) {
    this.mRepository.getPokemonSpeciesById(pokemonId, new ResponseCallBack<PokemonSpecies>() {
      @Override
      public void onSuccess(PokemonSpecies response) {
        if (response != null) {
          new Thread(() -> {
            String chosenFlavour = "";
            String country = Locale.getDefault().getLanguage().split("-")[0].toLowerCase();
            for (FlavourEntries flavour :
                response.getFlavourEntriesList()) {
              if (flavour.getLanguage().getName().contains(country)) {
                chosenFlavour = flavour.getFlavourText();

                break;
              }
            }

            if (chosenFlavour.isEmpty()) {
              for (FlavourEntries flavour :
                  response.getFlavourEntriesList()) {
                if (flavour.getLanguage().getName().contains("en")) {
                  chosenFlavour = flavour.getFlavourText();
                  break;
                }
              }
            }

            getPokemonDescriptionLiveData().postValue(chosenFlavour);
          }).start();

        }
      }

      @Override
      public void onFailure(String errorMessage) {
        getFecthErrorLiveData().postValue(errorMessage);
      }
    });
  }

  /**
   * Notifies that a view should be loading
   *
   * @param isLoading If it's loading or not
   */
  public void isLoading(boolean isLoading) {
    getIsLoadingMutableLiveData().postValue(isLoading);
  }

  /**
   * Deselect all PokemonResults from a list
   */
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

  /**
   * Selects an item from a list
   *
   * @param listId Item ID to select
   */
  public void setSelected(int listId) {
    getSelectedPokemonId().postValue(listId);
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

  /**
   * Notifies that a ViewPager has changed his page
   *
   * @param page    Page to change to
   * @param wasTurn If the Page was scrolled or forced to change
   */
  public void changePage(int page, boolean wasTurn) {
    String title = "";
    switch (page) {
      case 0:
        title = getApplication().getResources().getString(R.string.general_info);
        break;
      case 1:
        title = getApplication().getResources().getString(R.string.stats);
        break;
      case 2:
        title = getApplication().getResources().getString(R.string.moves);
        break;
    }
    getDetailsTitleLiveData().postValue(title);

    getDetailsPagerLiveData().postValue(page);

  }

  /**
   * Fetches a list of Moves
   *
   * @param pokemonMoves List of PokemonMoves to get their ID's
   */
  public void getMovesFromIds(List<PokemonMoves> pokemonMoves) {
    new Thread(() -> {
      List<String> movesIds = new ArrayList<>();
      for (PokemonMoves pokeMoves :
          pokemonMoves) {
        movesIds.add(pokeMoves.getMove().getUrlId());
      }
      mRepository.getMovesFromIds(movesIds, new ResponseCallBack<List<Moves>>() {
        @Override
        public void onSuccess(List<Moves> response) {
          getMovesInfo().postValue(response);
        }

        @Override
        public void onFailure(String errorMessage) {
          getMovesFromIdsAPI(movesIds);
        }
      });
    }).start();

  }

  /**
   * Fetches a list of Moves from the endpoint
   *
   * @param movesId List of move ID's
   */
  private void getMovesFromIdsAPI(List<String> movesId) {
    new Thread(() -> {
      List<Moves> movesList = new ArrayList<>();
      final int[] counter = {0};
      for (String moveId :
          movesId) {
        this.mRepository.getMoveById(moveId, new ResponseCallBack<Moves>() {
          @Override
          public void onSuccess(Moves response) {
            counter[0]++;
            movesList.add(response);
            if (movesList.size() == counter[0]) {
              getMovesInfo().postValue(movesList);
            }
          }

          @Override
          public void onFailure(String errorMessage) {

          }
        });
      }
    }).start();
  }

  /**
   * Get's counters and weaknesses from a type
   *
   * @param typeId    Type to get counters and weaknesses
   * @param show_type If the method was called in order to get counters or weaknesses
   */
  public void getTypeAndCounters(int typeId, Constants.SHOW_TYPE show_type) {
    new Thread(() -> {
      this.mRepository.getTypeInfoById(typeId, new ResponseCallBack<Type>() {
        @Override
        public void onSuccess(Type response) {
          getTypeMutableLiveData().postValue(response);
          getSHOW_typeMutableLiveData().postValue(show_type);
        }

        @Override
        public void onFailure(String errorMessage) {
          getFecthErrorLiveData().postValue(errorMessage);
        }
      });
    }).start();
  }

}