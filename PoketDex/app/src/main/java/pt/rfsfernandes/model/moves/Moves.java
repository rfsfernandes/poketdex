package pt.rfsfernandes.model.moves;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import pt.rfsfernandes.data.local.PokemonSpeciesTypeConverters;
import pt.rfsfernandes.data.local.PokemonTypeConverters;
import pt.rfsfernandes.model.SimpleModelData;
import pt.rfsfernandes.model.pokemon_species.FlavourEntries;

/**
 * Class Moves created at 1/19/21 16:51 for the project PoketDex
 * By: rodrigofernandes
 */
@Entity
@TypeConverters({PokemonSpeciesTypeConverters.class, PokemonTypeConverters.class})
public class Moves {
  @SerializedName("id")
  @PrimaryKey
  private int id;
  @SerializedName("effect_entries")
  private List<EffectsEntries> mEffectsEntriesList;
  @SerializedName("flavor_text_entries")
  private List<FlavourEntries> mFlavourEntriesList;
  @SerializedName("name")
  private String name;
  @SerializedName("power")
  private int power;
  @SerializedName("type")
  private SimpleModelData type;
  @SerializedName("effect_chance")
  private int effectChance;

  public Moves(int id, List<EffectsEntries> effectsEntriesList, List<FlavourEntries> flavourEntriesList, String name, int power, SimpleModelData type, int effectChance) {
    this.id = id;
    mEffectsEntriesList = effectsEntriesList;
    mFlavourEntriesList = flavourEntriesList;
    this.name = name;
    this.power = power;
    this.type = type;
    this.effectChance = effectChance;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public List<EffectsEntries> getEffectsEntriesList() {
    return mEffectsEntriesList;
  }

  public EffectsEntries getEffectEntry(){
    return mEffectsEntriesList.get(0);
  }

  public void setEffectsEntriesList(List<EffectsEntries> effectsEntriesList) {
    mEffectsEntriesList = effectsEntriesList;
  }

  public List<FlavourEntries> getFlavourEntriesList() {
    return mFlavourEntriesList;
  }

  public FlavourEntries getFlavourEntry(){
    return mFlavourEntriesList.get(0);
  }

  public void setFlavourEntriesList(List<FlavourEntries> flavourEntriesList) {
    mFlavourEntriesList = flavourEntriesList;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPower() {
    return power;
  }

  public void setPower(int power) {
    this.power = power;
  }

  public SimpleModelData getType() {
    return type;
  }

  public void setType(SimpleModelData type) {
    this.type = type;
  }

  public int getEffectChance() {
    return effectChance;
  }

  public void setEffectChance(int effectChance) {
    this.effectChance = effectChance;
  }

  @Override
  public String toString() {
    return "Moves{" +
        "id=" + id +
        ", mEffectsEntriesList=" + mEffectsEntriesList +
        ", mFlavourEntriesList=" + mFlavourEntriesList +
        ", name='" + name + '\'' +
        ", power=" + power +
        ", type=" + type +
        '}';
  }
}
