package pt.rfsfernandes.pocketdex.model.pokemon.sprites

import com.google.gson.annotations.SerializedName

/**
 * Class OfficialArtWork created at 1/16/21 15:30 for the project PoketDex
 * By: rodrigofernandes
 */
data class OfficialArtWork(@SerializedName("front_default") var frontDefault: String) {
    override fun toString(): String {
        return "OfficialArtWork{" +
                "frontDefault='" + frontDefault + '\'' +
                '}'
    }
}