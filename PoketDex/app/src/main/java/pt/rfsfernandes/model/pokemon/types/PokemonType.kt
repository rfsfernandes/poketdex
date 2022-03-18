package pt.rfsfernandes.model.pokemon.types

import pt.rfsfernandes.model.SimpleModelData
import com.google.gson.annotations.SerializedName
import androidx.room.TypeConverters
import pt.rfsfernandes.data.local.PokemonTypeConverters

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