package pt.rfsfernandes.model.pokemon_species;

import com.google.gson.annotations.SerializedName;

import androidx.room.TypeConverters;
import pt.rfsfernandes.data.local.PokemonTypeConverters;
import pt.rfsfernandes.model.SimpleModelData;

/**
 * Class FlavourEntries created at 1/17/21 20:02 for the project PoketDex
 * By: rodrigofernandes
 */
@TypeConverters(PokemonTypeConverters.class)
public class FlavourEntries {
  @SerializedName("flavor_text")
  private String flavourText;

  @SerializedName("language")
  private SimpleModelData language;

  public FlavourEntries(String flavourText, SimpleModelData language) {
    this.flavourText = flavourText;
    this.language = language;
  }

  public SimpleModelData getLanguage() {
    return language;
  }

  public void setLanguage(SimpleModelData language) {
    this.language = language;
  }

  public String getFlavourText() {
    return flavourText.replace("\n", " ");
  }

  public void setFlavourText(String flavourText) {
    this.flavourText = flavourText;
  }
}
