package pt.rfsfernandes.pocketdex.model.pokemon.types

import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import pt.rfsfernandes.pocketdex.data.local.PokemonTypeConverters
import pt.rfsfernandes.pocketdex.model.SimpleModelData

/**
 * Class PokemonType created at 1/16/21 15:34 for the project PoketDex
 * By: rodrigofernandes
 */
class PokemonType(
    @SerializedName("slot")
    var slot: Int,
    @TypeConverters(PokemonTypeConverters::class)
    @SerializedName("type")
    var type: SimpleModelData
) {

    override fun toString(): String {
        return "PokemonType{" +
                "slot=" + slot +
                ", type=" + type +
                '}'
    }
}