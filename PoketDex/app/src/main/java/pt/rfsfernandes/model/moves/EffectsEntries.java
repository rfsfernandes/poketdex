package pt.rfsfernandes.model.moves;

import com.google.gson.annotations.SerializedName;

/**
 * Class EffectsEntries created at 1/19/21 16:55 for the project PoketDex
 * By: rodrigofernandes
 */
public class EffectsEntries {
  @SerializedName("effect")
  private String effect;
  @SerializedName("short_effect")
  private String shortEffect;

  public EffectsEntries(String effect, String shortEffect) {
    this.effect = effect;
    this.shortEffect = shortEffect;
  }

  public String getEffect() {
    return effect;
  }

  public void setEffect(String effect) {
    this.effect = effect;
  }

  public String getShortEffect() {
    return shortEffect;
  }

  public void setShortEffect(String shortEffect) {
    this.shortEffect = shortEffect;
  }

  @Override
  public String toString() {
    return "EffectsEntries{" +
        "effect='" + effect + '\'' +
        ", shortEffect='" + shortEffect + '\'' +
        '}';
  }
}
