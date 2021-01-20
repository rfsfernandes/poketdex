package pt.rfsfernandes.data.repository;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pt.rfsfernandes.custom.callbacks.ResponseCallBack;
import pt.rfsfernandes.data.local.PokemonDAO;
import pt.rfsfernandes.data.remote.PokemonService;
import pt.rfsfernandes.model.moves.Moves;
import pt.rfsfernandes.model.pokemon.Pokemon;
import pt.rfsfernandes.model.type.Type;
import pt.rfsfernandes.model.pokemon_species.PokemonSpecies;
import pt.rfsfernandes.model.service_responses.PokemonListResponse;
import pt.rfsfernandes.model.service_responses.PokemonResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static pt.rfsfernandes.custom.Constants.ARTWORK_URL;

public class Repository {
  private final PokemonService mPokemonService;
  private final PokemonDAO mPokemonDAO;


  public Repository(PokemonService pokemonService, PokemonDAO pokemonDAO) {
    this.mPokemonService = pokemonService;
    this.mPokemonDAO = pokemonDAO;
  }

  /**
   * Fetches a list of pokemons. If there is a list already in the local database, should return
   * that list. If there isn't, should use API Endpoint
   * @param offset Where the list begins
   * @param limit Size of the list to be fetched
   * @param callBack Used when data is ready to be delivered
   */
  public void getPokemonList(int offset, int limit,
                             ResponseCallBack<List<PokemonResult>> callBack) {
    new Thread(() -> {

      List<PokemonResult> pokemonResultList = mPokemonDAO.getPokemonsFromOffsetWithLimit(offset + 1
          , offset + limit);

      if (pokemonResultList != null && pokemonResultList.size() != limit) {

        Call<PokemonListResponse> call = mPokemonService.getPokemonListPagination(offset, limit);

        call.enqueue(new Callback<PokemonListResponse>() {
          @Override
          public void onResponse(Call<PokemonListResponse> call, Response<PokemonListResponse> response) {
            if (response.isSuccessful()) {
              if (response.body() != null) {
                PokemonListResponse pokemonListResponse = response.body();
                List<PokemonResult> tempList = new ArrayList<>();
                for (int i = 0; i < pokemonListResponse.getResultList().size(); i++) {
                  PokemonResult pokemonResult = pokemonListResponse.getResultList().get(i);
                  tempList.add(new PokemonResult(pokemonResult.getName(), pokemonResult.getUrl(),
                      offset + (i + 1),
                      ARTWORK_URL.replace("{pokemonId}", String.valueOf(offset + (i + 1))), false));
                }
                new Thread(() -> {
                  mPokemonDAO.insertPokemonResults(tempList);
                  callBack.onSuccess(tempList);
                }).start();

              } else {
                callBack.onFailure(response.message());
              }
            } else {
              callBack.onFailure(response.message());
            }
          }

          @Override
          public void onFailure(Call<PokemonListResponse> call, Throwable t) {
            callBack.onFailure(t.getLocalizedMessage());
            Log.e("PokemonListError", t.getLocalizedMessage());
          }
        });

      } else {
        callBack.onSuccess(pokemonResultList);
      }

    }).start();


  }

  /**
   * Fetches a pokemon by id. If there is a pokemon with that ID already in the local database,
   * should return that pokemon. If there isn't, should use API Endpoint
   * @param id Pokemon ID
   * @param callBack Used when data is ready to be delivered
   */
  public void getPokemonById(int id, ResponseCallBack<Pokemon> callBack) {
    new Thread(() -> {
      Pokemon pokemon = mPokemonDAO.getPokemonById(id);
      if (pokemon == null) {
        Call<Pokemon> call = mPokemonService.getPokemonById(id);

        call.enqueue(new Callback<Pokemon>() {
          @Override
          public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
            if (response.isSuccessful()) {
              if (response.body() != null) {
                Pokemon pokemon = response.body();
                new Thread(() -> mPokemonDAO.insertPokemon(pokemon)).start();
                callBack.onSuccess(pokemon);
              } else {
                callBack.onFailure(response.message());
              }
            } else {
              callBack.onFailure(response.message());
            }
          }

          @Override
          public void onFailure(Call<Pokemon> call, Throwable t) {
            callBack.onFailure(t.getLocalizedMessage());
            Log.e("PokemonByIdError", t.getLocalizedMessage());
          }
        });
      } else {
        callBack.onSuccess(pokemon);
      }
    }).start();

  }

  /**
   * Fetches a pokemon species by pokemonId. If there is a species with that ID already in the
   * local database, should return that species. If there isn't, should use API Endpoint
   * @param pokemonId Pokemon ID
   * @param callBack Used when data is ready to be delivered
   */
  public void getPokemonSpeciesById(int pokemonId, ResponseCallBack<PokemonSpecies> callBack) {
    new Thread(() -> {
      PokemonSpecies pokemonSpecies = mPokemonDAO.getSpeciesByPokemonId(pokemonId);

      if (pokemonSpecies == null) {
        Call<PokemonSpecies> call = mPokemonService.getPokemonSpeciesById(pokemonId);
        call.enqueue(new Callback<PokemonSpecies>() {
          @Override
          public void onResponse(Call<PokemonSpecies> call, Response<PokemonSpecies> response) {
            if (response.isSuccessful()) {
              if (response.body() != null) {
                PokemonSpecies pokemonSpecies = response.body();
                pokemonSpecies.setId(pokemonId);
                new Thread(() -> mPokemonDAO.insertSpecies(pokemonSpecies)).start();
                callBack.onSuccess(pokemonSpecies);
              } else {
                callBack.onFailure(response.message());
              }
            } else {
              callBack.onFailure(response.message());
            }
          }

          @Override
          public void onFailure(Call<PokemonSpecies> call, Throwable t) {
            callBack.onFailure(t.getLocalizedMessage());
            Log.e("PokemonSpeciesByIdError", t.getLocalizedMessage());
          }
        });
      } else {
        callBack.onSuccess(pokemonSpecies);
      }
    }).start();

  }

  /**
   * Fetches a move by id. If there is a move with that ID already in the local database,
   * should return that move. If there isn't, should use API Endpoint
   * @param moveId Move ID
   * @param callBack Used when data is ready to be delivered
   */
  public void getMoveById(String moveId, ResponseCallBack<Moves> callBack) {
    if(!moveId.isEmpty()) {
      new Thread(() -> {
        Moves pokemonSpecies = mPokemonDAO.getMovesByMoveId(Integer.parseInt(moveId));
        if (pokemonSpecies == null) {
          Call<Moves> call = mPokemonService.getMoveById(Integer.parseInt(moveId));
          call.enqueue(new Callback<Moves>() {
            @Override
            public void onResponse(Call<Moves> call, Response<Moves> response) {
              if (response.isSuccessful()) {
                if (response.body() != null) {
                  Moves moves = response.body();
                  new Thread(() -> mPokemonDAO.insertMoves(moves)).start();
                  callBack.onSuccess(moves);
                } else {
                  callBack.onFailure(response.message());
                }
              } else {
                callBack.onFailure(response.message());
              }
            }

            @Override
            public void onFailure(Call<Moves> call, Throwable t) {
              callBack.onFailure(t.getLocalizedMessage());
              Log.e("PokemonMovesByIdError", t.getLocalizedMessage());
            }
          });
        } else {
          callBack.onSuccess(pokemonSpecies);
        }
      }).start();
    }

  }

  /**
   * Fetches a List of Moves from a List of movesId from the database
   * @param movesIds Moves IDs to fetch
   * @param callBack Used when data is ready to be delivered
   */
  public void getMovesFromIds(List<String> movesIds, ResponseCallBack<List<Moves>> callBack) {
    new Thread(() -> {
      List<Moves> movesList = mPokemonDAO.getMovesFromIdList(movesIds);

      if(movesList != null && movesList.size() != 0) {
        callBack.onSuccess(movesList);
      } else {
        callBack.onFailure("");
      }

    }).start();
  }

  /**
   * Fetches a Type by id. If there is a Type with that ID already in the local database,
   * should return that Type. If there isn't, should use API Endpoint
   * @param typeId Type ID
   * @param callBack Used when data is ready to be delivered
   */
  public void getTypeInfoById(int typeId, ResponseCallBack<Type> callBack){
    new Thread(() -> {
      Type type = mPokemonDAO.getTypeById(typeId);
      if(type == null) {
        Call<Type> call = mPokemonService.getTypeInfoById(typeId);
        call.enqueue(new Callback<Type>() {
          @Override
          public void onResponse(Call<Type> call, Response<Type> response) {
            if (response.isSuccessful()) {
              if (response.body() != null) {
                Type typeCall = response.body();
                new Thread(() -> mPokemonDAO.insertType(typeCall)).start();
                callBack.onSuccess(typeCall);
              } else {
                callBack.onFailure(response.message());
              }
            } else {
              callBack.onFailure(response.message());
            }
          }

          @Override
          public void onFailure(Call<Type> call, Throwable t) {
            callBack.onFailure(t.getLocalizedMessage());
            Log.e("PokemonTypeByIdError", t.getLocalizedMessage());
          }
        });
      } else {
        callBack.onSuccess(type);
      }
    }).start();
  }

}
