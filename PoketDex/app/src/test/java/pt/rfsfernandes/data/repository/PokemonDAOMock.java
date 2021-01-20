package pt.rfsfernandes.data.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pt.rfsfernandes.model.SimpleModelData;
import pt.rfsfernandes.model.moves.Moves;
import pt.rfsfernandes.model.pokemon.Pokemon;
import pt.rfsfernandes.model.pokemon_species.PokemonSpecies;
import pt.rfsfernandes.model.service_responses.PokemonResult;
import pt.rfsfernandes.model.type.Type;

import static pt.rfsfernandes.custom.Constants.ARTWORK_URL;
import static pt.rfsfernandes.data.repository.RepositoryTest.LIST_LENGTH_TEST;

/**
 * Class PokekmonDAOMock created at 1/20/21 19:22 for the project PoketDex
 * By: rodrigofernandes
 */
public class PokemonDAOMock {
  private final int[] availableIds = new int[15];
  private final List<PokemonResult> mPokemonResultListDB = new ArrayList<>();
  private final List<Pokemon> mPokemonListDB = new ArrayList<>();
  private final List<PokemonSpecies> mPokemonSpeciesListDB = new ArrayList<>();
  private final List<Moves> mMovesList = new ArrayList<>();
  private final List<Type> mTypeList = new ArrayList<>();

  public PokemonDAOMock() {
    List<Integer> numberList = new ArrayList<>();
    for (int i = 1; i <= LIST_LENGTH_TEST; i++) {
      numberList.add(i);
    }

    Collections.shuffle(numberList);

    for (int i = 0; i < availableIds.length; i++) {
      availableIds[i] = numberList.get(i);
    }

    generateDummyData();
  }

  /**
   * Calls all functions to generate dummy DB data
   */
  private void generateDummyData() {
    generateResultList();
    generatePokemonList();
    generatePokemonSpeciesList();
    generateMovesList();
    generateTypeList();
  }

  /**
   * Generates dummy data
   */
  private void generateResultList() {
    for (int i = 0; i < LIST_LENGTH_TEST / 4; i++) {
      int position = i + 1;
      mPokemonResultListDB.add(new PokemonResult(String.format("Teste %d", position), String.format("http://testeurl.com/%d/", position)
          , position,
          ARTWORK_URL.replace("{pokemonId}", String.valueOf(position)), false));
    }
  }

  /**
   * Mocks a DB fetch for a List Of PokemonResult
   *
   * @param offset Where the list begins
   * @param limit  Size of the list to be fetched
   * @return List of PokemonResult
   */
  public List<PokemonResult> getPokemonOffsetLimit(int offset, int limit) {
    List<PokemonResult> pokemonResults = new ArrayList<>();

    for (int i = offset - 1; i < (limit + offset - 1); i++) {
      try {
        pokemonResults.add(mPokemonResultListDB.get(i));
      } catch (Exception e) {
        pokemonResults = null;
        break;
      }
    }

    return pokemonResults;
  }

  /**
   * Mock a DB insert a list of PokemonResult
   *
   * @param newlist List to insert
   */
  public void insertPokemonResults(List<PokemonResult> newlist) {
    for (PokemonResult resultNew :
        newlist) {
      boolean found = false;

      for (PokemonResult result :
          mPokemonResultListDB) {
        if (resultNew.getListPosition() == result.getListPosition()) {
          found = true;
          break;
        }
      }

      if (!found) {
        mPokemonResultListDB.add(resultNew);
      }
    }

    Collections.sort(mPokemonResultListDB, (o1, o2) -> Integer.compare(o1.getListPosition(), o2.getListPosition()));
  }

  /**
   * Generates dummy data
   */
  private void generatePokemonList() {
    for (int i = 0; i < availableIds.length; i++) {
      int position = i + 1;
      Pokemon pokemon = new Pokemon(position, position, position, new ArrayList<>(),
          String.format("Pokemon test %d", position), position,
          new SimpleModelData(String.format("Species %d", position), String.format("http//testingurl.com/%d",
              position)), new ArrayList<>(), new ArrayList<>(), null);
      mPokemonListDB.add(pokemon);
    }
  }

  /**
   * Mocks a DB fetch to find a pokemon by it's ID
   *
   * @param id Pokemon id
   * @return Pokemon
   */
  public Pokemon getPokemonById(int id) {
    for (Pokemon pokemon :
        mPokemonListDB) {
      if (pokemon.getId() == id) {
        return pokemon;
      }
    }
    return null;
  }

  /**
   * Mock a DB insert of a Pokemon
   *
   * @param pokemon Pokemon to insert
   */
  public void insertPokemon(Pokemon pokemon) {

    boolean found = false;

    for (Pokemon pokemonFetch :
        mPokemonListDB) {
      if (pokemonFetch.getId() == pokemon.getId()) {
        found = true;
        break;
      }
    }

    if (!found) {
      mPokemonListDB.add(pokemon);
    }


    Collections.sort(mPokemonListDB, (o1, o2) -> Integer.compare(o1.getId(), o2.getId()));
  }

  /**
   * Generates dummy data
   */
  private void generatePokemonSpeciesList() {
    for (int i = 0; i < LIST_LENGTH_TEST / 4; i++) {
      int position = i + 1;
      mPokemonSpeciesListDB.add(new PokemonSpecies(position, new ArrayList<>()));
    }
  }

  /**
   * Mocks a DB fetch to find a PokemonSpecies by pokemon ID
   *
   * @param id Pokemon id
   * @return PokemonSpecies
   */
  public PokemonSpecies getPokemonSpeciesById(int id) {
    for (PokemonSpecies pokemonSpecies :
        mPokemonSpeciesListDB) {
      if (pokemonSpecies.getId() == id) {
        return pokemonSpecies;
      }
    }
    return null;
  }

  /**
   * Mock a DB insert of a PokemonSpecies
   *
   * @param pokemonSpecies PokemonSpecies to insert
   */
  public void insertSpecies(PokemonSpecies pokemonSpecies) {

    boolean found = false;

    for (PokemonSpecies species :
        mPokemonSpeciesListDB) {
      if (species.getId() == pokemonSpecies.getId()) {
        found = true;
        break;
      }
    }

    if (!found) {
      mPokemonSpeciesListDB.add(pokemonSpecies);
    }


    Collections.sort(mPokemonSpeciesListDB, (o1, o2) -> Integer.compare(o1.getId(), o2.getId()));
  }

  /**
   * Generates dummy data
   */
  private void generateMovesList() {
    for (int i = 0; i < LIST_LENGTH_TEST / 4; i++) {
      int position = i + 1;
      mMovesList.add(new Moves(position, new ArrayList<>(), new ArrayList<>(), String.format(
          "Test %d", position), position, null, position));
    }
  }

  /**
   * Mocks a DB fetch to find a Moves by it's ID
   *
   * @param id Moves id
   * @return Moves
   */
  public Moves getMovesById(int id) {
    for (Moves moves :
        mMovesList) {
      if (moves.getId() == id) {
        return moves;
      }
    }
    return null;
  }

  /**
   * Mock a DB insert of a Moves
   *
   * @param moves Moves to insert
   */
  public void insertMoves(Moves moves) {

    boolean found = false;

    for (Moves move :
        mMovesList) {
      if (move.getId() == moves.getId()) {
        found = true;
        break;
      }
    }

    if (!found) {
      mMovesList.add(moves);
    }


    Collections.sort(mMovesList, (o1, o2) -> Integer.compare(o1.getId(), o2.getId()));
  }

  /**
   * Mocks a DB fetch to get a List of moves from a List of Ids
   *
   * @param ids List of ids
   * @return List of Moves
   */
  public List<Moves> getMovesByIdList(List<String> ids) {
    List<Moves> movesList = new ArrayList<>();

    for (Moves moves :
        mMovesList) {
      boolean found = false;

      for (String moveId :
          ids) {

        if (moves.getId() == Integer.parseInt(moveId)) {
          found = true;
          break;
        }
      }

      if (found) {
        movesList.add(moves);
      }
    }

    return movesList;
  }

  /**
   * Mock a DB insert a list of PokemonResult
   *
   * @param newlist List to insert
   */
  public void insertMovesList(List<Moves> newlist) {
    for (Moves resultNew :
        newlist) {
      boolean found = false;

      for (Moves result :
          mMovesList) {
        if (resultNew.getId() == result.getId()) {
          found = true;
          break;
        }
      }

      if (!found) {
        mMovesList.add(resultNew);
      }
    }

    Collections.sort(mMovesList, (o1, o2) -> Integer.compare(o1.getId(), o2.getId()));
  }

  /**
   * Generates dummy data
   */
  private void generateTypeList() {
    for (int i = 0; i < LIST_LENGTH_TEST / 4; i++) {
      int position = i + 1;
      mTypeList.add(new Type(position, null, String.format("Test %d", position)));
    }
  }

  /**
   * Mocks a DB fetch to find a Type by it's ID
   *
   * @param id Type id
   * @return Type
   */
  public Type getTypeById(int id) {
    for (Type type :
        mTypeList) {
      if (type.getId() == id) {
        return type;
      }
    }
    return null;
  }

  /**
   * Mock a DB insert of a Type
   *
   * @param type Type to insert
   */
  public void insertType(Type type) {

    boolean found = false;

    for (Type mType :
        mTypeList) {
      if (mType.getId() == type.getId()) {
        found = true;
        break;
      }
    }

    if (!found) {
      mTypeList.add(type);
    }


    Collections.sort(mTypeList, (o1, o2) -> Integer.compare(o1.getId(), o2.getId()));
  }

}
