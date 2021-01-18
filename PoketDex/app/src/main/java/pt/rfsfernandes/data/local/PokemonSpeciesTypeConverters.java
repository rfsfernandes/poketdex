package pt.rfsfernandes.data.local;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import androidx.room.TypeConverter;
import pt.rfsfernandes.model.SimpleModelData;
import pt.rfsfernandes.model.pokemon.abilities.Ability;
import pt.rfsfernandes.model.pokemon.moves.Moves;
import pt.rfsfernandes.model.pokemon.sprites.OfficialArtWork;
import pt.rfsfernandes.model.pokemon.sprites.OtherSpriteInfo;
import pt.rfsfernandes.model.pokemon.sprites.PokemonSprites;
import pt.rfsfernandes.model.pokemon.stats.Stats;
import pt.rfsfernandes.model.pokemon.types.PokemonType;
import pt.rfsfernandes.model.pokemon_species.FlavourEntries;
import pt.rfsfernandes.model.service_responses.PokemonResult;

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

}
