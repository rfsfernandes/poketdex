package pt.rfsfernandes.pocketdex.model.pokemon.stats

import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import pt.rfsfernandes.pocketdex.data.local.PokemonTypeConverters
import pt.rfsfernandes.pocketdex.model.SimpleModelData

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