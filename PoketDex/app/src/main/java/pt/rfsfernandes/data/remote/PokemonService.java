package pt.rfsfernandes.data.remote;

import pt.rfsfernandes.model.moves.Moves;
import pt.rfsfernandes.model.pokemon.Pokemon;
import pt.rfsfernandes.model.pokemon_species.PokemonSpecies;
import pt.rfsfernandes.model.service_responses.PokemonListResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokemonService {

  @GET("pokemon")
  Call<PokemonListResponse> getPokemonListPagination(@Query("offset") int offeset,
                                                     @Query("limit") int limit);

  @GET("pokemon/{pokemonId}")
  Call<Pokemon> getPokemonById(@Path("pokemonId") int pokemonId);

  @GET("pokemon-species/{pokemonId}")
  Call<PokemonSpecies> getPokemonSpeciesById(@Path("pokemonId") int pokemonId);

  @GET("move/{moveId}")
  Call<Moves> getMoveById(@Path("moveId") int moveId);
}