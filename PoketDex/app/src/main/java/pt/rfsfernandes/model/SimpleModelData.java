package pt.rfsfernandes.model;

import com.google.gson.annotations.SerializedName;

/**
 * Class SimpleModelData created at 1/16/21 15:00 for the project PoketDex
 * By: rodrigofernandes
 */
public class SimpleModelData {
  @SerializedName("name")
  private String name;
  @SerializedName("url")
  private String url;

  public SimpleModelData(String name, String url) {
    this.name = name;
    this.url = url;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrlId() {
    StringBuilder id = new StringBuilder();
    int counter = 0;

    for (int i = url.length() - 1; i < url.length(); i--) {
      if (url.charAt(i) == '/') {
        counter++;
      } else {
        id.append(url.charAt(i));
      }

      if (counter == 2) {
        break;
      }
    }
    return new StringBuilder(id.toString()).reverse().toString();
  }

  @Override
  public String toString() {
    return "SimpleModelData{" +
        "name='" + name + '\'' +
        '}';
  }
}
