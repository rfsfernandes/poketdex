package pt.rfsfernandes.model.moves

import com.google.gson.annotations.SerializedName
import androidx.room.TypeConverters
import pt.rfsfernandes.data.local.PokemonSpeciesTypeConverters
import pt.rfsfernandes.data.local.PokemonTypeConverters
import pt.rfsfernandes.model.moves.EffectsEntries
import pt.rfsfernandes.model.pokemon_species.FlavourEntries
import pt.rfsfernandes.model.SimpleModelData
import androidx.room.PrimaryKey

/**
 * Class EffectsEntries created at 1/19/21 16:55 for the project PoketDex
 * By: rodrigofernandes
 */
data class EffectsEntries(
        @SerializedName("effect")
        var effect: String,
        @SerializedName("short_effect")
        var shortEffect: String
) {

    override fun toString(): String {
        return "EffectsEntries{" +
                "effect='" + effect + '\'' +
                ", shortEffect='" + shortEffect + '\'' +
                '}'
    }
}