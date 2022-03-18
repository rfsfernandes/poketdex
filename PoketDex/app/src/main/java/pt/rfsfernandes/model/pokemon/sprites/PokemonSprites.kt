package pt.rfsfernandes.model.pokemon.sprites

import pt.rfsfernandes.model.pokemon.sprites.OfficialArtWork
import com.google.gson.annotations.SerializedName
import androidx.room.TypeConverters
import pt.rfsfernandes.data.local.PokemonTypeConverters
import pt.rfsfernandes.model.pokemon.sprites.OtherSpriteInfo

/**
 * Class PokemonSprites created at 1/16/21 15:21 for the project PoketDex
 * By: rodrigofernandes
 */
data class PokemonSprites(
        @SerializedName("front_default")
        var frontDefault: String,
        @SerializedName("front_shiny")
        var frontShiny: String,
        @TypeConverters(PokemonTypeConverters::class)
        @SerializedName("other")
        var otherInfo: OtherSpriteInfo
) {

    override fun toString(): String {
        return "PokemonSprites{" +
                "frontDefault='" + frontDefault + '\'' +
                ", frontShiny='" + frontShiny + '\'' +
                ", otherInfo=" + otherInfo +
                '}'
    }
}