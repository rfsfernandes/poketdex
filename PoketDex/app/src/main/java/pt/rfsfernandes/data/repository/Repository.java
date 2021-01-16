package pt.rfsfernandes.data.repository;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import pt.rfsfernandes.R;
import pt.rfsfernandes.data.remote.DataSource;
import pt.rfsfernandes.data.remote.PokemonService;
import pt.rfsfernandes.model.service_responses.PokemonListResponse;
import pt.rfsfernandes.model.service_responses.PokemonResult;
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
        if(response.isSuccessful()) {
          if(response.body() != null) {
            PokemonListResponse pokemonListResponse = response.body();
            callBack.onSuccess(pokemonListResponse);
          } else {
            callBack.onFailure("");
          }
        } else {
          callBack.onFailure(response.message());
        }
      }

      @Override
      public void onFailure(Call<PokemonListResponse> call, Throwable t) {
        callBack.onFailure(t.getLocalizedMessage());
      }
    });

  }

}
