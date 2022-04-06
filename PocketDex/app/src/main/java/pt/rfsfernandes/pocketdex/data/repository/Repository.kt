package pt.rfsfernandes.pocketdex.data.repository

import kotlinx.coroutines.flow.Flow
import pt.rfsfernandes.pocketdex.model.Resource
import pt.rfsfernandes.pocketdex.model.moves.Moves
import pt.rfsfernandes.pocketdex.model.pokemon.Pokemon
import pt.rfsfernandes.pocketdex.model.pokemon_species.PokemonSpecies
import pt.rfsfernandes.pocketdex.model.service_responses.PokemonResult
import pt.rfsfernandes.pocketdex.model.type.Type

interface Repository {
    suspend fun getWholePokemonList(): Flow<Resource<List<PokemonResult?>?>>
    suspend fun getPokemonList(offset: Int, limit: Int): Flow<Resource<List<PokemonResult?>?>>
    suspend fun getPokemonById(id: Int): Flow<Resource<Pokemon?>>
    suspend fun getPokemonSpeciesById(pokemonId: Int): Flow<Resource<PokemonSpecies?>>
    suspend fun getMoveById(moveId: String): Flow<Resource<Moves?>>
    suspend fun getMovesFromIds(movesIds: List<String?>?): Flow<Resource<List<Moves?>?>>
    suspend fun getTypeInfoById(typeId: Int): Flow<Resource<Type?>>
    suspend fun getPokemonByQuery(
        query: String
    ): Flow<Resource<List<PokemonResult?>?>>
}