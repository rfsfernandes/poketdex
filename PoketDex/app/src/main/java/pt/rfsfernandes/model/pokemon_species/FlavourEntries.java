package pt.rfsfernandes.model.pokemon_species;

import com.google.gson.annotations.SerializedName;

/**
 * Class FlavourEntries created at 1/17/21 20:02 for the project PoketDex
 * By: rodrigofernandes
 */
public class FlavourEntries {
  @SerializedName("flavor_text")
  private String flavourText;

  public FlavourEntries(String flavourText) {
    this.flavourText = flavourText;
  }

  public String getFlavourText() {
    return flavourText.replace("\n", " ");
  }

  public void setFlavourText(String flavourText) {
    this.flavourText = flavourText;
  }
}
