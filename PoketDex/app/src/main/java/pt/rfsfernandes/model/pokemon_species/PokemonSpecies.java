package pt.rfsfernandes.model.pokemon_species;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Class PokemonSpecies created at 1/17/21 20:00 for the project PoketDex
 * By: rodrigofernandes
 */
public class PokemonSpecies {
  @SerializedName("flavor_text_entries")
  private List<FlavourEntries> mFlavourEntriesList;

  public PokemonSpecies(List<FlavourEntries> flavourEntriesList) {
    mFlavourEntriesList = flavourEntriesList;
  }

  public List<FlavourEntries> getFlavourEntriesList() {
    return mFlavourEntriesList;
  }

  public void setFlavourEntriesList(List<FlavourEntries> flavourEntriesList) {
    mFlavourEntriesList = flavourEntriesList;
  }
}
