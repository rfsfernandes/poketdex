package pt.rfsfernandes.data.repository;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pt.rfsfernandes.data.local.PokemonDAO;
import pt.rfsfernandes.data.remote.PokemonService;
import pt.rfsfernandes.model.pokemon.Pokemon;
import pt.rfsfernandes.model.pokemon_species.PokemonSpecies;
import pt.rfsfernandes.model.service_responses.PokemonListResponse;
import pt.rfsfernandes.model.service_responses.PokemonResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
  private final PokemonService mPokemonService;
  private final PokemonDAO mPokemonDAO;

  public Repository(PokemonService pokemonService, PokemonDAO pokemonDAO) {
    this.mPokemonService = pokemonService;
    this.mPokemonDAO = pokemonDAO;
  }


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
                  tempList.add(new PokemonResult(pokemonResult.getName(), offset + (i + 1),
                      pokemonResult.getPokemonImage(), false));
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

  public void getPokemonById(int id, ResponseCallBack<Pokemon> callBack) {
    new Thread(() -> {
      Pokemon pokemon = mPokemonDAO.getPokemonById(id);
      if(pokemon == null) {
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

  public void getPokemonSpecies(int pokemonId, ResponseCallBack<PokemonSpecies> callBack) {
    new Thread(() -> {
      PokemonSpecies pokemonSpecies = mPokemonDAO.getSpeciesByPokemonId(pokemonId);

      if(pokemonSpecies == null) {
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


}
