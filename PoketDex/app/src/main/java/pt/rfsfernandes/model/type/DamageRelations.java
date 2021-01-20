package pt.rfsfernandes.model.type;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.room.TypeConverters;
import pt.rfsfernandes.data.local.PokemonTypeConverters;
import pt.rfsfernandes.model.SimpleModelData;

/**
 * Class DamageRelations created at 1/20/21 01:07 for the project PoketDex
 * By: rodrigofernandes
 */
@TypeConverters(PokemonTypeConverters.class)
public class DamageRelations {
  @SerializedName("double_damage_from")
  private List<SimpleModelData> doubleDamageFrom;

  @SerializedName("double_damage_to")
  private List<SimpleModelData> doubleDamageTo;

  @SerializedName("half_damage_from")
  private List<SimpleModelData> halfDamageFrom;

  @SerializedName("half_damage_to")
  private List<SimpleModelData> halfDamageTo;

  @SerializedName("no_damage_from")
  private List<SimpleModelData> noDamageFrom;

  @SerializedName("no_damage_to")
  private List<SimpleModelData> noDamageTo;

  public DamageRelations(List<SimpleModelData> doubleDamageFrom, List<SimpleModelData> doubleDamageTo, List<SimpleModelData> halfDamageFrom, List<SimpleModelData> halfDamageTo, List<SimpleModelData> noDamageFrom, List<SimpleModelData> noDamageTo) {
    this.doubleDamageFrom = doubleDamageFrom;
    this.doubleDamageTo = doubleDamageTo;
    this.halfDamageFrom = halfDamageFrom;
    this.halfDamageTo = halfDamageTo;
    this.noDamageFrom = noDamageFrom;
    this.noDamageTo = noDamageTo;
  }

  public List<SimpleModelData> getDoubleDamageFrom() {
    return doubleDamageFrom;
  }

  public void setDoubleDamageFrom(List<SimpleModelData> doubleDamageFrom) {
    this.doubleDamageFrom = doubleDamageFrom;
  }

  public List<SimpleModelData> getDoubleDamageTo() {
    return doubleDamageTo;
  }

  public void setDoubleDamageTo(List<SimpleModelData> doubleDamageTo) {
    this.doubleDamageTo = doubleDamageTo;
  }

  public List<SimpleModelData> getHalfDamageFrom() {
    return halfDamageFrom;
  }

  public void setHalfDamageFrom(List<SimpleModelData> halfDamageFrom) {
    this.halfDamageFrom = halfDamageFrom;
  }

  public List<SimpleModelData> getHalfDamageTo() {
    return halfDamageTo;
  }

  public void setHalfDamageTo(List<SimpleModelData> halfDamageTo) {
    this.halfDamageTo = halfDamageTo;
  }

  public List<SimpleModelData> getNoDamageFrom() {
    return noDamageFrom;
  }

  public void setNoDamageFrom(List<SimpleModelData> noDamageFrom) {
    this.noDamageFrom = noDamageFrom;
  }

  public List<SimpleModelData> getNoDamageTo() {
    return noDamageTo;
  }

  public void setNoDamageTo(List<SimpleModelData> noDamageTo) {
    this.noDamageTo = noDamageTo;
  }

  @Override
  public String toString() {
    return "DamageRelations{" +
        "doubleDamageFrom=" + doubleDamageFrom +
        ", doubleDamageTo=" + doubleDamageTo +
        ", halfDamageFrom=" + halfDamageFrom +
        ", halfDamageTo=" + halfDamageTo +
        ", noDamageFrom=" + noDamageFrom +
        ", noDamageTo=" + noDamageTo +
        '}';
  }
}
