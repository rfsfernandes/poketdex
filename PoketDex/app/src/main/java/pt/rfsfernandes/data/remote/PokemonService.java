package pt.rfsfernandes.data.remote;

import java.util.List;

import pt.rfsfernandes.model.SimpleModelData;
import pt.rfsfernandes.model.pokemon.Pokemon;
import pt.rfsfernandes.model.service_responses.PokemonListResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public interface PokemonService {
	 /**
		* User, for example:
		*
		* @GET("/api/v1/login")
		*
		*   Call<ResponseObject> login(@Body User user, @Header("Language") String language);
		*
		* To login using a given url.
		*/
	@GET("pokemon")
  Call<PokemonListResponse> getPokemonListPagination(@Query("offset") int offeset,
                                               @Query("limit") int limit);

	@GET("pokemon/{pokemonId}")
  Call<Pokemon> getPokemonById(@Path("pokemonId") int pokemonId);

}