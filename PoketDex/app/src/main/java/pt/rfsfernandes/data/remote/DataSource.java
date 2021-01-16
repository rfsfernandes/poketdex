package pt.rfsfernandes.data.remote;

import pt.rfsfernandes.custom.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DataSource {

  private static final String BASE_URL = Constants.BASE_URL;

  private static final Retrofit retrofit = new Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build();

  private static PokemonService sPokemonService;

  public static PokemonService getPokemonService() {
    if (sPokemonService == null) {
      sPokemonService = retrofit.create(PokemonService.class);
    }
    return sPokemonService;
  }

}