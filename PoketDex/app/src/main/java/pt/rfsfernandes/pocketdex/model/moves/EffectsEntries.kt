package pt.rfsfernandes.pocketdex.model.moves

import com.google.gson.annotations.SerializedName

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