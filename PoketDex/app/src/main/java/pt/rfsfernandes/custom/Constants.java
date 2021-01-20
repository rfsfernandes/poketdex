package pt.rfsfernandes.custom;

public class Constants {
  public static final String BASE_URL = "https://pokeapi.co/api/v2/";
  public static final String ARTWORK_URL = "https://raw.githubusercontent" +
      ".com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/{pokemonId}.png";
  public static final int RESULT_LIMIT = 50;

  public enum MOVES_ITEM {
    SIMPLE,
    TYPE,
  }

  public enum SHOW_TYPE {
    POKEMON,
    MOVE
  }

}
