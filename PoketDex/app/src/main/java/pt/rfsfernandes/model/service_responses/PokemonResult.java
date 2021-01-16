package pt.rfsfernandes.model.service_responses;

import pt.rfsfernandes.model.SimpleModelData;

/**
 * Class PokemonResult created at 1/16/21 15:47 for the project PoketDex
 * By: rodrigofernandes
 */
public class PokemonResult extends SimpleModelData {
  private int listPosition;

  public PokemonResult(String name) {
    super(name);
  }


  public int getListPosition() {
    return listPosition;
  }

  public void setListPosition(int listPosition) {
    this.listPosition = listPosition;
  }

  @Override
  public String toString() {
    return "PokemonResult{" +
        "listPosition=" + listPosition +
        '}';
  }
}
