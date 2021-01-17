package pt.rfsfernandes.data.repository;

import android.util.Log;

import pt.rfsfernandes.data.remote.PokemonService;
import pt.rfsfernandes.model.pokemon.Pokemon;
import pt.rfsfernandes.model.pokemon_species.PokemonSpecies;
import pt.rfsfernandes.model.service_responses.PokemonListResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
  private final PokemonService mPokemonService;

  public Repository(PokemonService pokemonService) {
    this.mPokemonService = pokemonService;
  }


  public void getPokemonList(int offset, int limit,
                             ResponseCallBack<PokemonListResponse> callBack) {
    Call<PokemonListResponse> call = this.mPokemonService.getPokemonListPagination(offset, limit);

    call.enqueue(new Callback<PokemonListResponse>() {
      @Override
      public void onResponse(Call<PokemonListResponse> call, Response<PokemonListResponse> response) {
        if (response.isSuccessful()) {
          if (response.body() != null) {
            PokemonListResponse pokemonListResponse = response.body();
            callBack.onSuccess(pokemonListResponse);
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

  }

  public void getPokemonById(int id, ResponseCallBack<Pokemon> callBack) {
    Call<Pokemon> call = this.mPokemonService.getPokemonById(id);

    call.enqueue(new Callback<Pokemon>() {
      @Override
      public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
        if (response.isSuccessful()) {
          if (response.body() != null) {
            Pokemon pokemon = response.body();
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

  }

  public void getPokemonSpecies(int pokemonId, ResponseCallBack<PokemonSpecies> callBack) {
    Call<PokemonSpecies> call = this.mPokemonService.getPokemonSpeciesById(pokemonId);
    call.enqueue(new Callback<PokemonSpecies>() {
      @Override
      public void onResponse(Call<PokemonSpecies> call, Response<PokemonSpecies> response) {
        if (response.isSuccessful()) {
          if (response.body() != null) {
            PokemonSpecies pokemonSpecies = response.body();
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
        Log.e("PokemonByIdError", t.getLocalizedMessage());
      }
    });

  }


}
