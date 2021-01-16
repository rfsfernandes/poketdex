package pt.rfsfernandes.model.pokemon.sprites;

import com.google.gson.annotations.SerializedName;

/**
 * Class OtherSpriteInfo created at 1/16/21 15:28 for the project PoketDex
 * By: rodrigofernandes
 */
public class OtherSpriteInfo {
  @SerializedName("official-artwork")
  private OfficialArtWork officialArtWork;

  public OtherSpriteInfo(OfficialArtWork officialArtWork) {
    this.officialArtWork = officialArtWork;
  }

  public OfficialArtWork getOfficialArtWork() {
    return officialArtWork;
  }

  public void setOfficialArtWork(OfficialArtWork officialArtWork) {
    this.officialArtWork = officialArtWork;
  }

  @Override
  public String toString() {
    return "OtherSpriteInfo{" +
        "officialArtWork=" + officialArtWork +
        '}';
  }
}
