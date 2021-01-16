package pt.rfsfernandes.model.service_responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Class PokemonListResponse created at 1/16/21 15:41 for the project PoketDex
 * By: rodrigofernandes
 */
public class PokemonListResponse {
  @SerializedName("count")
  private int count;

  @SerializedName("next")
  private String nextPage;

  @SerializedName("previous")
  private String previousPage;

  @SerializedName("results")
  private List<PokemonResult> resultList;

  public PokemonListResponse(int count, String nextPage, String previousPage, List<PokemonResult> resultList) {
    this.count = count;
    this.nextPage = nextPage;
    this.previousPage = previousPage;
    this.resultList = resultList;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public String getNextPage() {
    return nextPage;
  }

  public void setNextPage(String nextPage) {
    this.nextPage = nextPage;
  }

  public String getPreviousPage() {
    return previousPage;
  }

  public void setPreviousPage(String previousPage) {
    this.previousPage = previousPage;
  }

  public List<PokemonResult> getResultList() {
    return resultList;
  }

  public void setResultList(List<PokemonResult> resultList) {
    this.resultList = resultList;
  }

  @Override
  public String toString() {
    return "PokemonListResponse{" +
        "count=" + count +
        ", nextPage='" + nextPage + '\'' +
        ", previousPage='" + previousPage + '\'' +
        ", resultList=" + resultList +
        '}';
  }
}
