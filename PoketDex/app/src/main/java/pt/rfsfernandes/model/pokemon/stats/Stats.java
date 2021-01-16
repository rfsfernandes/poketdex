package pt.rfsfernandes.model.pokemon.stats;

import com.google.gson.annotations.SerializedName;

import pt.rfsfernandes.model.SimpleModelData;

/**
 * Class Stats created at 1/16/21 15:31 for the project PoketDex
 * By: rodrigofernandes
 */
public class Stats {
  @SerializedName("base_stat")
  private int baseStat;

  @SerializedName("stat")
  private SimpleModelData stat;

  public Stats(int baseStat, SimpleModelData stat) {
    this.baseStat = baseStat;
    this.stat = stat;
  }

  public int getBaseStat() {
    return baseStat;
  }

  public void setBaseStat(int baseStat) {
    this.baseStat = baseStat;
  }

  public SimpleModelData getStat() {
    return stat;
  }

  public void setStat(SimpleModelData stat) {
    this.stat = stat;
  }

  @Override
  public String toString() {
    return "Stats{" +
        "baseStat=" + baseStat +
        ", stat=" + stat +
        '}';
  }
}
