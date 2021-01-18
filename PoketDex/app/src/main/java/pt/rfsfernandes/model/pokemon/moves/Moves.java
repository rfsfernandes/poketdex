package pt.rfsfernandes.model.pokemon.moves;

import com.google.gson.annotations.SerializedName;

import androidx.room.TypeConverters;
import pt.rfsfernandes.data.local.PokemonTypeConverters;
import pt.rfsfernandes.model.SimpleModelData;

/**
 * Class Moves created at 1/16/21 15:18 for the project PoketDex
 * By: rodrigofernandes
 */
public class Moves {
  @SerializedName("move")
  @TypeConverters(PokemonTypeConverters.class)
  private SimpleModelData move;

  public Moves(SimpleModelData move) {
    this.move = move;
  }

  public SimpleModelData getMove() {
    return move;
  }

  public void setMove(SimpleModelData move) {
    this.move = move;
  }

  @Override
  public String toString() {
    return "Moves{" +
        "move=" + move +
        '}';
  }
}
