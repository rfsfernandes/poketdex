package pt.rfsfernandes.data.repository;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pt.rfsfernandes.model.moves.Moves;
import pt.rfsfernandes.model.pokemon.Pokemon;
import pt.rfsfernandes.model.pokemon_species.PokemonSpecies;
import pt.rfsfernandes.model.service_responses.PokemonResult;
import pt.rfsfernandes.model.type.Type;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class RepositoryTest {
  public static int LIST_LENGTH_TEST = 200;
  private PokemonDAOMock mPokemonDAOMock;
  private PokemonServiceMock mPokemonServiceMock;

  @Before
  public void setUp() {
    mPokemonDAOMock = new PokemonDAOMock();
    mPokemonServiceMock = new PokemonServiceMock();
  }

  @Test
  public void testGetPokemonList() {
    int limit = 5;

    assertTrue(pokemonListFromDBorAPI(5, limit));

    assertFalse(pokemonListFromDBorAPI(51, limit));

    assertTrue(pokemonListFromDBorAPI(51, limit));

    assertEquals(getPokemonList(51, limit).size(), limit);

    assertEquals(getPokemonList(24, limit).size(), limit);

  }

  /**
   * Mocks a DB fetch for a List Of PokemonResult
   *
   * @param offset Where the list begins
   * @param limit  Size of the list to be fetched
   * @return True if pokemonResult came from DB, False if came from API
   */
  private boolean pokemonListFromDBorAPI(int offset, int limit) {
    List<PokemonResult> resultList = mPokemonDAOMock.getPokemonOffsetLimit(offset, limit);

    if (resultList == null) {
      resultList = mPokemonServiceMock.getPokemonOffsetLimit(offset, limit);
      mPokemonDAOMock.insertPokemonResults(resultList);
      return false;
    } else {
      return true;
    }
  }

  /**
   * Mocks a DB fetch for a List Of PokemonResult
   *
   * @param offset Where the list begins
   * @param limit  Size of the list to be fetched
   * @return List of pokemon list
   */
  private List<PokemonResult> getPokemonList(int offset, int limit) {
    List<PokemonResult> resultList = mPokemonDAOMock.getPokemonOffsetLimit(offset, limit);

    if (resultList == null) {
      resultList = mPokemonServiceMock.getPokemonOffsetLimit(offset, limit);
      mPokemonDAOMock.insertPokemonResults(resultList);
    }
    return resultList;
  }

  @Test
  public void testGetPokemonById() {
    int pokemonId = 5;

    assertTrue(pokemonFromDBorAPI(pokemonId));

    pokemonId = 51;
    assertFalse(pokemonFromDBorAPI(pokemonId));

    Pokemon pokemon = getPokemonById(pokemonId);
    assertNotNull(pokemon);

    assertEquals(pokemon.getId(), pokemonId);

    pokemonId = 40;
    pokemon = getPokemonById(pokemonId);
    assertNotNull(pokemon);
    assertEquals(pokemon.getId(), pokemonId);
  }

  /**
   * Mocks a DB fetch for a Pokemon by Id
   *
   * @param id pokemon Id
   * @return True if pokemon came from DB, False if came from API
   */
  private boolean pokemonFromDBorAPI(int id) {
    Pokemon pokemon = mPokemonDAOMock.getPokemonById(id);

    if (pokemon == null) {
      pokemon = mPokemonServiceMock.getPokemonById(id);
      mPokemonDAOMock.insertPokemon(pokemon);
      return false;
    } else {
      return true;
    }

  }

  /**
   * Mocks a DB fetch for a Pokemon by Id
   *
   * @param id pokemon Id
   * @return Pokemon from given Id
   */
  private Pokemon getPokemonById(int id) {
    Pokemon pokemon = mPokemonDAOMock.getPokemonById(id);

    if (pokemon == null) {
      pokemon = mPokemonServiceMock.getPokemonById(id);
      mPokemonDAOMock.insertPokemon(pokemon);
    }

    return pokemon;

  }

  @Test
  public void testGetPokemonSpeciesById() {
    int speciesId = 5;

    assertTrue(speciesFromDBorAPI(speciesId));

    speciesId = 51;
    assertFalse(speciesFromDBorAPI(speciesId));

    PokemonSpecies pokemonSpecies = getSpeciesById(speciesId);
    assertNotNull(pokemonSpecies);

    assertEquals(pokemonSpecies.getId(), speciesId);

    speciesId = 40;
    pokemonSpecies = getSpeciesById(speciesId);
    assertNotNull(pokemonSpecies);
    assertEquals(pokemonSpecies.getId(), speciesId);
  }

  /**
   * Mocks a DB fetch for a Move by Id
   *
   * @param id Move Id
   * @return True if Move came from DB, False if came from API
   */
  private boolean speciesFromDBorAPI(int id) {
    PokemonSpecies species = mPokemonDAOMock.getPokemonSpeciesById(id);

    if (species == null) {
      species = mPokemonServiceMock.getSpeciesById(id);
      mPokemonDAOMock.insertSpecies(species);
      return false;
    } else {
      return true;
    }

  }

  /**
   * Mocks a DB fetch for a Pokemon by Id
   *
   * @param id pokemon Id
   * @return Pokemon from given Id
   */
  private PokemonSpecies getSpeciesById(int id) {
    PokemonSpecies species = mPokemonDAOMock.getPokemonSpeciesById(id);

    if (species == null) {
      species = mPokemonServiceMock.getSpeciesById(id);
      mPokemonDAOMock.insertSpecies(species);
    }

    return species;

  }

  @Test
  public void testGetMoveById() {
    int moveId = 5;

    assertTrue(moveFromDBorAPI(moveId));

    moveId = 51;
    assertFalse(moveFromDBorAPI(moveId));

    Moves moves = getMoveById(moveId);
    assertNotNull(moves);

    assertEquals(moves.getId(), moveId);

    moveId = 40;
    moves = getMoveById(moveId);
    assertNotNull(moves);
    assertEquals(moves.getId(), moveId);
  }

  /**
   * Mocks a DB fetch for a Move by Id
   *
   * @param id Move Id
   * @return True if Move came from DB, False if came from API
   */
  private boolean moveFromDBorAPI(int id) {
    Moves moves = mPokemonDAOMock.getMovesById(id);

    if (moves == null) {
      moves = mPokemonServiceMock.getMovesById(id);
      mPokemonDAOMock.insertMoves(moves);
      return false;
    } else {
      return true;
    }

  }

  /**
   * Mocks a DB fetch for a Pokemon by Id
   *
   * @param id pokemon Id
   * @return Pokemon from given Id
   */
  private Moves getMoveById(int id) {
    Moves pokemon = mPokemonDAOMock.getMovesById(id);

    if (pokemon == null) {
      pokemon = mPokemonServiceMock.getMovesById(id);
      mPokemonDAOMock.insertMoves(pokemon);
    }

    return pokemon;

  }

  @Test
  public void testGetMovesFromIds() {
    List<String> movesIds = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      movesIds.add(String.valueOf(new Random().nextInt(50 - i) + i));
    }

    assertTrue(movesListFromDBorAPI(movesIds));

    movesIds.clear();
    for (int i = 50; i < LIST_LENGTH_TEST / 3; i++) {
      movesIds.add(String.valueOf(new Random().nextInt((LIST_LENGTH_TEST / 3) - i) + i));
    }
    assertFalse(movesListFromDBorAPI(movesIds));

    assertNotNull(getMovesListWithMovesId(movesIds));

    movesIds.clear();
    for (int i = 50; i < LIST_LENGTH_TEST / 3; i++) {
      movesIds.add(String.valueOf(new Random().nextInt((LIST_LENGTH_TEST / 3) - i) + i));
    }
    assertNotNull(getMovesListWithMovesId(movesIds));
  }

  /**
   * Mocks a DB fetch for a List Of Moves
   *
   * @param listIds List of ids
   * @return List of pokemon list
   */
  private boolean movesListFromDBorAPI(List<String> listIds) {
    List<Moves> movesList = mPokemonDAOMock.getMovesByIdList(listIds);

    if (listIds.size() != movesList.size()) {
      movesList = mPokemonServiceMock.getMovesByIdList(listIds);
      mPokemonDAOMock.insertMovesList(movesList);
      return false;
    } else {
      return true;
    }

  }

  /**
   * Mocks a DB fetch for a List Of Moves
   *
   * @param listIds List of ids
   * @return True if Moves came from DB, False if came from API
   */
  private List<Moves> getMovesListWithMovesId(List<String> listIds) {
    List<Moves> movesList = mPokemonDAOMock.getMovesByIdList(listIds);

    if (movesList == null) {
      movesList = mPokemonServiceMock.getMovesByIdList(listIds);
      mPokemonDAOMock.insertMovesList(movesList);
    }
    return movesList;
  }

  @Test
  public void testGetTypeInfoById() {
    int typeId = 5;

    assertTrue(typeFromDBorAPI(typeId));

    typeId = 51;
    assertFalse(typeFromDBorAPI(typeId));

    Type pokemon = getTypeById(typeId);
    assertNotNull(pokemon);

    assertEquals(pokemon.getId(), typeId);

    typeId = 40;
    pokemon = getTypeById(typeId);
    assertNotNull(pokemon);
    assertEquals(pokemon.getId(), typeId);
  }

  /**
   * Mocks a DB fetch for a Type by Id
   *
   * @param id Type Id
   * @return True if pokemon came from DB, False if came from API
   */
  private boolean typeFromDBorAPI(int id) {
    Type type = mPokemonDAOMock.getTypeById(id);

    if (type == null) {
      type = mPokemonServiceMock.getTypeById(id);
      mPokemonDAOMock.insertType(type);
      return false;
    } else {
      return true;
    }

  }

  /**
   * Mocks a DB fetch for a Type by Id
   *
   * @param id Type Id
   * @return Type from given Id
   */
  private Type getTypeById(int id) {
    Type type = mPokemonDAOMock.getTypeById(id);

    if (type == null) {
      type = mPokemonServiceMock.getTypeById(id);
      mPokemonDAOMock.insertType(type);
    }

    return type;

  }
}