package pt.rfsfernandes.pocketdex.model.pokemon_species

import androidx.room.TypeConverters
import pt.rfsfernandes.pocketdex.data.local.PokemonTypeConverters
import pt.rfsfernandes.pocketdex.model.SimpleModelData
import com.google.gson.annotations.SerializedName

/**
 * Class FlavourEntries created at 1/17/21 20:02 for the project PoketDex
 * By: rodrigofernandes
 */
@TypeConverters(PokemonTypeConverters::class)
class FlavourEntries(@SerializedName("flavor_text") private var flavourText: String, @SerializedName("language") var language: SimpleModelData) {
    fun getFlavourText(): String {
        return flavourText.replace("\n", " ")
    }

    fun setFlavourText(flavourText: String) {
        this.flavourText = flavourText
    }
}