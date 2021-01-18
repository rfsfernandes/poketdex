package pt.rfsfernandes.model.pokemon.abilities;

import com.google.gson.annotations.SerializedName;

import androidx.room.TypeConverters;
import pt.rfsfernandes.data.local.PokemonTypeConverters;
import pt.rfsfernandes.model.SimpleModelData;

/**
 * Class Ability created at 1/16/21 14:43 for the project PoketDex
 * By: rodrigofernandes
 */
public class Ability {
  @SerializedName("ability")
  @TypeConverters(PokemonTypeConverters.class)
  private SimpleModelData ability;

  @SerializedName("is_hidden")
  private boolean isHidden;

  @SerializedName("slot")
  private int slot;

  public Ability(SimpleModelData ability, boolean isHidden, int slot) {
    this.ability = ability;
    this.isHidden = isHidden;
    this.slot = slot;
  }

  public SimpleModelData getAbility() {
    return ability;
  }

  public void setAbility(SimpleModelData ability) {
    this.ability = ability;
  }

  public boolean isHidden() {
    return isHidden;
  }

  public void setHidden(boolean hidden) {
    isHidden = hidden;
  }

  public int getSlot() {
    return slot;
  }

  public void setSlot(int slot) {
    this.slot = slot;
  }

  @Override
  public String toString() {
    return "Ability{" +
        "ability=" + ability +
        ", isHidden=" + isHidden +
        ", slot=" + slot +
        '}';
  }

}
