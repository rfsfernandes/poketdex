package pt.rfsfernandes.data.local;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import pt.rfsfernandes.model.moves.Moves;
import pt.rfsfernandes.model.pokemon.Pokemon;
import pt.rfsfernandes.model.pokemon_species.PokemonSpecies;
import pt.rfsfernandes.model.service_responses.PokemonResult;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface PokemonDAO {

  @Query("SELECT *\n" +
      "FROM pokemonresult\n" +
      "WHERE listPosition BETWEEN :from AND :to")
  List<PokemonResult> getPokemonsFromOffsetWithLimit(int from, int to);

  @Insert(onConflict = REPLACE)
  void insertPokemonResults(List<PokemonResult> pokemonResult);

  @Query("SELECT * FROM Pokemon WHERE id = :id")
  Pokemon getPokemonById(int id);

  @Insert(onConflict = REPLACE)
  void insertPokemon(Pokemon pokemon);

  @Query("SELECT * FROM PokemonSpecies WHERE id = :id")
  PokemonSpecies getSpeciesByPokemonId(int id);

  @Insert(onConflict = REPLACE)
  void insertSpecies(PokemonSpecies pokemonSpecies);

  @Query("SELECT * FROM Moves WHERE id = :id")
  Moves getMovesByMoveId(int id);

  @Query("SELECT * FROM Moves WHERE id IN (:idsList)")
  List<Moves> getMovesFromIdList(List<String> idsList);

  @Insert(onConflict = REPLACE)
  void insertMoves(Moves moves);

}
