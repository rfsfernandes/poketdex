package pt.rfsfernandes.pocketdex.model.pokemon.sprites

import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import pt.rfsfernandes.pocketdex.data.local.PokemonTypeConverters

/**
 * Class OtherSpriteInfo created at 1/16/21 15:28 for the project PoketDex
 * By: rodrigofernandes
 */
data class OtherSpriteInfo(
    @TypeConverters(PokemonTypeConverters::class)
    @SerializedName("official-artwork")
    var officialArtWork: OfficialArtWork
) {
    override fun toString(): String {
        return "OtherSpriteInfo{" +
                "officialArtWork=" + officialArtWork +
                '}'
    }
}