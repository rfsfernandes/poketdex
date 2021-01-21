package pt.rfsfernandes.model.type;

import com.google.gson.annotations.SerializedName;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import pt.rfsfernandes.data.local.PokemonTypeConverters;

/**
 * Class Type created at 1/19/21 23:50 for the project PoketDex
 * By: rodrigofernandes
 */
@Entity
@TypeConverters(PokemonTypeConverters.class)
public class Type {
  @SerializedName("id")
  @PrimaryKey
  private int id;

  @SerializedName("damage_relations")
  private DamageRelations mDamageRelations;

  @SerializedName("name")
  private String name;

  public Type(int id, DamageRelations damageRelations, String name) {
    this.id = id;
    this.mDamageRelations = damageRelations;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public DamageRelations getDamageRelations() {
    return mDamageRelations;
  }

  public void setDamageRelations(DamageRelations damageRelations) {
    this.mDamageRelations = damageRelations;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
