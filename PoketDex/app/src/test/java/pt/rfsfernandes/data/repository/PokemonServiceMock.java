package pt.rfsfernandes.data.repository;

import java.util.ArrayList;
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
 * Class PokemonServiceMock created at 1/20/21 19:22 for the project PoketDex
 * By: rodrigofernandes
 */
public class PokemonServiceMock {

  private final List<PokemonResult> mPokemonResultListAPI = new ArrayList<>();
  private final List<Pokemon> mPokemonListAPI = new ArrayList<>();
  private final List<PokemonSpecies> mPokemonSpeciesListAPI = new ArrayList<>();
  private final List<Moves> mMovesListAPI = new ArrayList<>();
  private final List<Type> mTypeListAPI = new ArrayList<>();

  public PokemonServiceMock() {
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
   * Generates dummy API data
   */
  private void generateResultList() {
    for (int i = 0; i < LIST_LENGTH_TEST; i++) {
      int position = i + 1;
      mPokemonResultListAPI.add(new PokemonResult(String.format("Teste %d", position), String.format("http://testeurl.com/%d/", position)
          , position,
          ARTWORK_URL.replace("{pokemonId}", String.valueOf(position)), false));
    }
  }

  /**
   * Mocks an API response of getting PokemonResults with an offset and a limit
   *
   * @param offset Where the list begins
   * @param limit  Size of the list to be fetched
   * @return List of PokemonResult
   */
  public List<PokemonResult> getPokemonOffsetLimit(int offset, int limit) {

    List<PokemonResult> pokemonResults = new ArrayList<>();

    for (int i = offset; i < (limit + offset); i++) {
      if (i + 1 <= mPokemonResultListAPI.size()) {
        pokemonResults.add(mPokemonResultListAPI.get(i));
      }

    }

    if (pokemonResults.size() > 0) {
      return pokemonResults;
    } else {
      return new ArrayList<>();
    }

  }

  /**
   * Generates dummy data
   */
  private void generatePokemonList() {
    for (int i = 0; i < LIST_LENGTH_TEST * 3; i++) {
      int position = i + 1;
      Pokemon pokemon = new Pokemon(position, position, position, new ArrayList<>(),
          String.format("Pokemon test %d", position), position,
          new SimpleModelData(String.format("Species %d", position), String.format("http//testingurl.com/%d",
              position)), new ArrayList<>(), new ArrayList<>(), null);
      mPokemonListAPI.add(pokemon);
    }
  }

  /**
   * Mocks an API fetch to find a pokemon by it's ID
   *
   * @param id Pokemon id
   * @return Pokemon
   */
  public Pokemon getPokemonById(int id) {
    for (Pokemon pokemon :
        mPokemonListAPI) {
      if (pokemon.getId() == id) {
        return pokemon;
      }
    }
    return null;
  }

  /**
   * Generates dummy data
   */
  private void generatePokemonSpeciesList() {
    for (int i = 0; i < LIST_LENGTH_TEST; i++) {
      int position = i + 1;
      mPokemonSpeciesListAPI.add(new PokemonSpecies(position, new ArrayList<>()));
    }
  }

  /**
   * Mocks an API fetch to find a PokemonSpecies by pokemon ID
   *
   * @param id Pokemon id
   * @return PokemonSpecies
   */
  public PokemonSpecies getSpeciesById(int id) {
    for (PokemonSpecies species :
        mPokemonSpeciesListAPI) {
      if (species.getId() == id) {
        return species;
      }
    }
    return null;
  }

  /**
   * Generates dummy data
   */
  private void generateMovesList() {
    for (int i = 0; i < LIST_LENGTH_TEST; i++) {
      int position = i + 1;
      mMovesListAPI.add(new Moves(position, new ArrayList<>(), new ArrayList<>(), String.format(
          "Test %d", position), position, null, position));
    }
  }

  /**
   * Mocks an API fetch to find a Move by it's ID
   *
   * @param id Pokemon id
   * @return Pokemon
   */
  public Moves getMovesById(int id) {
    for (Moves moves :
        mMovesListAPI) {
      if (moves.getId() == id) {
        return moves;
      }
    }
    return null;
  }

  /**
   * Mocks an API fetch to get a List of moves from a List of Ids
   *
   * @param ids List of ids
   * @return List of Moves
   */
  public List<Moves> getMovesByIdList(List<String> ids) {
    List<Moves> movesList = new ArrayList<>();

    for (Moves moves :
        mMovesListAPI) {
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
   * Generates dummy data
   */
  private void generateTypeList() {
    for (int i = 0; i < LIST_LENGTH_TEST; i++) {
      int position = i + 1;
      mTypeListAPI.add(new Type(position, null, String.format("Test %d", position)));
    }
  }

  /**
   * Mocks an API fetch to find a pokemon by it's ID
   *
   * @param id Pokemon id
   * @return Pokemon
   */
  public Type getTypeById(int id) {
    for (Type type :
        mTypeListAPI) {
      if (type.getId() == id) {
        return type;
      }
    }
    return null;
  }


}
