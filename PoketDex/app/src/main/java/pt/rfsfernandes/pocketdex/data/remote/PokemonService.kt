package pt.rfsfernandes.pocketdex.data.remote

import pt.rfsfernandes.pocketdex.model.moves.Moves
import pt.rfsfernandes.pocketdex.model.pokemon.Pokemon
import pt.rfsfernandes.pocketdex.model.pokemon_species.PokemonSpecies
import pt.rfsfernandes.pocketdex.model.service_responses.PokemonListResponse
import pt.rfsfernandes.pocketdex.model.type.Type
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {
    @GET("pokemon")
    fun getPokemonListPagination(
        @Query("offset") offeset: Int,
        @Query("limit") limit: Int
    ): Response<PokemonListResponse?>?

    @GET("pokemon/{pokemonId}")
    fun getPokemonById(@Path("pokemonId") pokemonId: Int): Response<Pokemon?>?

    @GET("pokemon-species/{pokemonId}")
    fun getPokemonSpeciesById(@Path("pokemonId") pokemonId: Int): Response<PokemonSpecies?>?

    @GET("move/{moveId}")
    fun getMoveById(@Path("moveId") moveId: Int): Response<Moves?>?

    @GET("type/{typeId}")
    fun getTypeInfoById(@Path("typeId") typeId: Int): Response<Type?>?
}