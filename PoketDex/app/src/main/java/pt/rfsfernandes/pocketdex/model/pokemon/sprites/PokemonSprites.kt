package pt.rfsfernandes.pocketdex.model.pokemon.sprites

import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import pt.rfsfernandes.pocketdex.data.local.PokemonTypeConverters

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