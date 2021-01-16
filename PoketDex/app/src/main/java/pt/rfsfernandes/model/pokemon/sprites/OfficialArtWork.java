package pt.rfsfernandes.model.pokemon.sprites;

import com.google.gson.annotations.SerializedName;

/**
 * Class OfficialArtWork created at 1/16/21 15:30 for the project PoketDex
 * By: rodrigofernandes
 */
public class OfficialArtWork {
  @SerializedName("front_default")
  private String frontDefault;

  public OfficialArtWork(String frontDefault) {
    this.frontDefault = frontDefault;
  }

  public String getFrontDefault() {
    return frontDefault;
  }

  public void setFrontDefault(String frontDefault) {
    this.frontDefault = frontDefault;
  }

  @Override
  public String toString() {
    return "OfficialArtWork{" +
        "frontDefault='" + frontDefault + '\'' +
        '}';
  }
}
