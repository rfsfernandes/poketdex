package pt.rfsfernandes.pocketdex.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pt.rfsfernandes.pocketdex.model.moves.Moves
import pt.rfsfernandes.pocketdex.model.pokemon.Pokemon
import pt.rfsfernandes.pocketdex.model.pokemon_species.PokemonSpecies
import pt.rfsfernandes.pocketdex.model.service_responses.PokemonResult
import pt.rfsfernandes.pocketdex.model.type.Type

@Dao
interface PokemonDAO {
    @Query("SELECT * FROM pokemonresult WHERE listPosition BETWEEN :from AND :to")
    suspend fun getPokemonsFromOffsetWithLimit(from: Int, to: Int): List<PokemonResult>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonResults(pokemonResult: List<PokemonResult?>?)

    @Query("SELECT * FROM Pokemon WHERE id = :id")
    suspend fun getPokemonById(id: Int): Pokemon?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: Pokemon?)

    @Query("SELECT * FROM PokemonSpecies WHERE id = :id")
    suspend fun getSpeciesByPokemonId(id: Int): PokemonSpecies?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpecies(pokemonSpecies: PokemonSpecies?)

    @Query("SELECT * FROM Moves WHERE id = :id")
    suspend fun getMovesByMoveId(id: Int): Moves?

    @Query("SELECT * FROM Moves WHERE id IN (:idsList)")
    suspend fun getMovesFromIdList(idsList: List<String?>?): List<Moves?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoves(moves: Moves?)

    @Query("SELECT * FROM Type WHERE id = :id")
    suspend fun getTypeById(id: Int): Type?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertType(type: Type?)
}