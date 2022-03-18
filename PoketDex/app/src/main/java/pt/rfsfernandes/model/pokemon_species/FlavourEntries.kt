package pt.rfsfernandes.model.pokemon_species

import androidx.room.TypeConverters
import pt.rfsfernandes.data.local.PokemonTypeConverters
import pt.rfsfernandes.model.SimpleModelData
import com.google.gson.annotations.SerializedName
import pt.rfsfernandes.data.local.PokemonSpeciesTypeConverters
import pt.rfsfernandes.model.pokemon_species.FlavourEntries
import androidx.room.PrimaryKey

/**
 * Class FlavourEntries created at 1/17/21 20:02 for the project PoketDex
 * By: rodrigofernandes
 */
@TypeConverters(PokemonTypeConverters::class)
class FlavourEntries(@field:SerializedName("flavor_text") private var flavourText: String, @field:SerializedName("language") var language: SimpleModelData) {
    fun getFlavourText(): String {
        return flavourText.replace("\n", " ")
    }

    fun setFlavourText(flavourText: String) {
        this.flavourText = flavourText
    }
}