package pt.rfsfernandes.data.remote;

import pt.rfsfernandes.model.pokemon.Pokemon;
import pt.rfsfernandes.model.service_responses.PokemonListResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokemonService {
  /**
   * User, for example:
   *
   * @GET("/api/v1/login") Call<ResponseObject> login(@Body User user, @Header("Language") String language);
   * <p>
   * To login using a given url.
   */
  @GET("pokemon")
  Call<PokemonListResponse> getPokemonListPagination(@Query("offset") int offeset,
                                                     @Query("limit") int limit);

  @GET("pokemon/{pokemonId}")
  Call<Pokemon> getPokemonById(@Path("pokemonId") int pokemonId);

}