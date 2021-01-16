package pt.rfsfernandes.model;

import com.google.gson.annotations.SerializedName;

/**
 * Class SimpleModelData created at 1/16/21 15:00 for the project PoketDex
 * By: rodrigofernandes
 */
public class SimpleModelData {
  @SerializedName("name")
  private String name;

  public SimpleModelData(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "SimpleModelData{" +
        "name='" + name + '\'' +
        '}';
  }
}
