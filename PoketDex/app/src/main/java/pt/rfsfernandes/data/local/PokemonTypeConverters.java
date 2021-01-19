package pt.rfsfernandes.data.local;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import androidx.room.TypeConverter;
import pt.rfsfernandes.model.SimpleModelData;
import pt.rfsfernandes.model.pokemon.abilities.Ability;
import pt.rfsfernandes.model.pokemon.moves.PokemonMoves;
import pt.rfsfernandes.model.pokemon.sprites.OfficialArtWork;
import pt.rfsfernandes.model.pokemon.sprites.OtherSpriteInfo;
import pt.rfsfernandes.model.pokemon.sprites.PokemonSprites;
import pt.rfsfernandes.model.pokemon.stats.Stats;
import pt.rfsfernandes.model.pokemon.types.PokemonType;
import pt.rfsfernandes.model.service_responses.PokemonResult;

public class PokemonTypeConverters {
  // Pokemon Result
  @TypeConverter
  public static List<PokemonResult> stringToPokemonResultList(String data) {
    if (data == null) {
      return Collections.emptyList();
    }

    Type listType = new TypeToken<List<PokemonResult>>() {
    }.getType();

    return new Gson().fromJson(data, listType);
  }

  @TypeConverter
  public static String pokemonResultToString(List<PokemonResult> someObjects) {
    return new Gson().toJson(someObjects);
  }

  // SimpleModelData
  @TypeConverter
  public static List<SimpleModelData> stringToSimpleModelDataList(String data) {
    if (data == null) {
      return Collections.emptyList();
    }

    Type listType = new TypeToken<List<SimpleModelData>>() {
    }.getType();

    return new Gson().fromJson(data, listType);
  }

  @TypeConverter
  public static String simpleModelDataListToString(List<SimpleModelData> someObjects) {
    return new Gson().toJson(someObjects);
  }

  @TypeConverter
  public static SimpleModelData stringToSimpleModelData(String data) {
    if (data == null) {
      return new SimpleModelData("", "");
    }

    Type listType = new TypeToken<SimpleModelData>() {
    }.getType();

    return new Gson().fromJson(data, listType);
  }

  @TypeConverter
  public static String simpleModelDataToString(SimpleModelData someObjects) {
    return new Gson().toJson(someObjects);
  }

  // Ability
  @TypeConverter
  public static List<Ability> stringToAbility(String data) {
    if (data == null) {
      return Collections.emptyList();
    }

    Type listType = new TypeToken<List<Ability>>() {
    }.getType();

    return new Gson().fromJson(data, listType);
  }

  @TypeConverter
  public static String abilityToString(List<Ability> someObjects) {
    return new Gson().toJson(someObjects);
  }

  // Moves
  @TypeConverter
  public static List<PokemonMoves> stringToMoves(String data) {
    if (data == null) {
      return Collections.emptyList();
    }

    Type listType = new TypeToken<List<PokemonMoves>>() {
    }.getType();

    return new Gson().fromJson(data, listType);
  }

  @TypeConverter
  public static String movesToString(List<PokemonMoves> someObjects) {
    return new Gson().toJson(someObjects);
  }

  // Stats
  @TypeConverter
  public static List<Stats> stringToStats(String data) {
    if (data == null) {
      return Collections.emptyList();
    }

    Type listType = new TypeToken<List<Stats>>() {
    }.getType();

    return new Gson().fromJson(data, listType);
  }

  @TypeConverter
  public static String statsToString(List<Stats> someObjects) {
    return new Gson().toJson(someObjects);
  }

  // PokemonType
  @TypeConverter
  public static List<PokemonType> stringToPokemonType(String data) {
    if (data == null) {
      return Collections.emptyList();
    }

    Type listType = new TypeToken<List<PokemonType>>() {
    }.getType();

    return new Gson().fromJson(data, listType);
  }

  @TypeConverter
  public static String pokemonTypeToString(List<PokemonType> someObjects) {
    return new Gson().toJson(someObjects);
  }

  // PokemonSprites
  @TypeConverter
  public static PokemonSprites stringToPokemonSprites(String data) {
    if (data == null) {
      return new PokemonSprites("", "", new OtherSpriteInfo(new OfficialArtWork("")));
    }

    Type listType = new TypeToken<PokemonSprites>() {
    }.getType();

    return new Gson().fromJson(data, listType);
  }

  @TypeConverter
  public static String pokemonSpritesToString(PokemonSprites someObjects) {
    return new Gson().toJson(someObjects);
  }

  // OtherSpriteInfo
  @TypeConverter
  public static List<OtherSpriteInfo> stringToOtherSpriteInfo(String data) {
    if (data == null) {
      return Collections.emptyList();
    }

    Type listType = new TypeToken<List<OtherSpriteInfo>>() {
    }.getType();

    return new Gson().fromJson(data, listType);
  }

  @TypeConverter
  public static String otherSpriteInfoToString(List<OtherSpriteInfo> someObjects) {
    return new Gson().toJson(someObjects);
  }

  // OfficialArtWork
  @TypeConverter
  public static List<OfficialArtWork> stringToOfficialArtWork(String data) {
    if (data == null) {
      return Collections.emptyList();
    }

    Type listType = new TypeToken<List<OfficialArtWork>>() {
    }.getType();

    return new Gson().fromJson(data, listType);
  }

  @TypeConverter
  public static String officialArtWorkToString(List<OfficialArtWork> someObjects) {
    return new Gson().toJson(someObjects);
  }


}
