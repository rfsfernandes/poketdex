package pt.rfsfernandes.model.pokemon.stats

import pt.rfsfernandes.model.SimpleModelData
import com.google.gson.annotations.SerializedName
import androidx.room.TypeConverters
import pt.rfsfernandes.data.local.PokemonTypeConverters

/**
 * Class Stats created at 1/16/21 15:31 for the project PoketDex
 * By: rodrigofernandes
 */
data class Stats(
        @SerializedName("base_stat")
        var baseStat: Int,
        @TypeConverters(PokemonTypeConverters::class)
        @SerializedName("stat")
        var stat: SimpleModelData
) {

    override fun toString(): String {
        return "Stats{" +
                "baseStat=" + baseStat +
                ", stat=" + stat +
                '}'
    }
}