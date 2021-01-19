package pt.rfsfernandes.model.pokemon_species;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import pt.rfsfernandes.data.local.PokemonSpeciesTypeConverters;

/**
 * Class PokemonSpecies created at 1/17/21 20:00 for the project PoketDex
 * By: rodrigofernandes
 */
@Entity
@TypeConverters(PokemonSpeciesTypeConverters.class)
public class PokemonSpecies {
  @PrimaryKey
  private int id;
  @SerializedName("flavor_text_entries")
  private List<FlavourEntries> mFlavourEntriesList;

  public PokemonSpecies(int id, List<FlavourEntries> flavourEntriesList) {
    this.id = id;
    mFlavourEntriesList = flavourEntriesList;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public List<FlavourEntries> getFlavourEntriesList() {
    return mFlavourEntriesList;
  }

  public void setFlavourEntriesList(List<FlavourEntries> flavourEntriesList) {
    mFlavourEntriesList = flavourEntriesList;
  }
}
