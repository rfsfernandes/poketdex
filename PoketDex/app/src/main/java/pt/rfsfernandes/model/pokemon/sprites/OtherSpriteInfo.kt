package pt.rfsfernandes.model.pokemon.sprites

import pt.rfsfernandes.model.pokemon.sprites.OfficialArtWork
import com.google.gson.annotations.SerializedName
import androidx.room.TypeConverters
import pt.rfsfernandes.data.local.PokemonTypeConverters
import pt.rfsfernandes.model.pokemon.sprites.OtherSpriteInfo

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