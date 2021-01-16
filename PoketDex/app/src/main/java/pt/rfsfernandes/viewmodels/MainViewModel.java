package pt.rfsfernandes.viewmodels;

import android.app.Application;
import android.content.Context;


import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import pt.rfsfernandes.custom.Constants;
import pt.rfsfernandes.data.remote.DataSource;
import pt.rfsfernandes.data.repository.Repository;
import pt.rfsfernandes.data.repository.ResponseCallBack;
import pt.rfsfernandes.model.service_responses.PokemonListResponse;
import pt.rfsfernandes.model.service_responses.PokemonResult;

import static pt.rfsfernandes.custom.Constants.RESULT_LIMIT;


public class MainViewModel extends ViewModel {
  private int currentOffset = 0;
  private final Repository mRepository = new Repository(DataSource.getPokemonService());
  private final MutableLiveData<PokemonListResponse> mPokemonListResponseMutableLiveData =
      new MutableLiveData<>();

  private final MutableLiveData<String> mFecthErrorLiveData = new MutableLiveData<>();

  public MutableLiveData<PokemonListResponse> getPokemonListResponseMutableLiveData() {
    return mPokemonListResponseMutableLiveData;
  }

  public MutableLiveData<String> getFecthErrorLiveData() {
    return mFecthErrorLiveData;
  }

  public void loadResults() {
    this.mRepository.getPokemonList(currentOffset, RESULT_LIMIT, new ResponseCallBack<PokemonListResponse>() {
      @Override
      public void onSuccess(PokemonListResponse response) {
        for (int i = 0; i < response.getResultList().size(); i++) {
          PokemonResult pokemonResult = response.getResultList().get(i);
          pokemonResult.setListPosition(currentOffset + (i + 1));
        }
        getPokemonListResponseMutableLiveData().setValue(response);
        if(response.getNextPage() != null) {
          currentOffset += RESULT_LIMIT;
        }
      }

      @Override
      public void onFailure(String errorMessage) {
        getFecthErrorLiveData().setValue(errorMessage);
      }
    });
  }

}