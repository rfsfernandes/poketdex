package pt.rfsfernandes.model.pokemon.types;

import com.google.gson.annotations.SerializedName;

import pt.rfsfernandes.model.SimpleModelData;

/**
 * Class PokemonType created at 1/16/21 15:34 for the project PoketDex
 * By: rodrigofernandes
 */
public class PokemonType {
  @SerializedName("slot")
  private int slot;

  @SerializedName("type")
  private SimpleModelData type;

  public PokemonType(int slot, SimpleModelData type) {
    this.slot = slot;
    this.type = type;
  }

  public int getSlot() {
    return slot;
  }

  public void setSlot(int slot) {
    this.slot = slot;
  }

  public SimpleModelData getType() {
    return type;
  }

  public void setType(SimpleModelData type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "PokemonType{" +
        "slot=" + slot +
        ", type=" + type +
        '}';
  }
}
