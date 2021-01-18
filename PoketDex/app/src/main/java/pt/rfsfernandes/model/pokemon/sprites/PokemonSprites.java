package pt.rfsfernandes.model.pokemon.sprites;

import com.google.gson.annotations.SerializedName;

import androidx.room.TypeConverters;
import pt.rfsfernandes.data.local.PokemonTypeConverters;

/**
 * Class PokemonSprites created at 1/16/21 15:21 for the project PoketDex
 * By: rodrigofernandes
 */
public class PokemonSprites {
  @SerializedName("front_default")
  private String frontDefault;

  @SerializedName("front_shiny")
  private String frontShiny;

  @SerializedName("other")
  @TypeConverters(PokemonTypeConverters.class)
  private OtherSpriteInfo otherInfo;

  public PokemonSprites(String frontDefault, String frontShiny, OtherSpriteInfo otherInfo) {
    this.frontDefault = frontDefault;
    this.frontShiny = frontShiny;
    this.otherInfo = otherInfo;
  }

  public String getFrontDefault() {
    return frontDefault;
  }

  public void setFrontDefault(String frontDefault) {
    this.frontDefault = frontDefault;
  }

  public String getFrontShiny() {
    return frontShiny;
  }

  public void setFrontShiny(String frontShiny) {
    this.frontShiny = frontShiny;
  }

  public OtherSpriteInfo getOtherInfo() {
    return otherInfo;
  }

  public void setOtherInfo(OtherSpriteInfo otherInfo) {
    this.otherInfo = otherInfo;
  }

  @Override
  public String toString() {
    return "PokemonSprites{" +
        "frontDefault='" + frontDefault + '\'' +
        ", frontShiny='" + frontShiny + '\'' +
        ", otherInfo=" + otherInfo +
        '}';
  }
}
