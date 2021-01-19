package pt.rfsfernandes.data.local;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import androidx.room.TypeConverter;
import pt.rfsfernandes.model.moves.EffectsEntries;
import pt.rfsfernandes.model.pokemon_species.FlavourEntries;

public class PokemonSpeciesTypeConverters {
  // FlavourEntries
  @TypeConverter
  public static List<FlavourEntries> stringToPoFlavourEntriesList(String data) {
    if (data == null) {
      return Collections.emptyList();
    }

    Type listType = new TypeToken<List<FlavourEntries>>() {
    }.getType();

    return new Gson().fromJson(data, listType);
  }

  @TypeConverter
  public static String flavourEntriesToString(List<FlavourEntries> someObjects) {
    return new Gson().toJson(someObjects);
  }

  // EffectsEntries
  @TypeConverter
  public static List<EffectsEntries> stringToEffectsEntriesList(String data) {
    if (data == null) {
      return Collections.emptyList();
    }

    Type listType = new TypeToken<List<EffectsEntries>>() {
    }.getType();

    return new Gson().fromJson(data, listType);
  }

  @TypeConverter
  public static String effectsEntriesToString(List<EffectsEntries> someObjects) {
    return new Gson().toJson(someObjects);
  }


}
