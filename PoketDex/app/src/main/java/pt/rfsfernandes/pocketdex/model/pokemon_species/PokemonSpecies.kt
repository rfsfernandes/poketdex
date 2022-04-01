package pt.rfsfernandes.pocketdex.model.pokemon_species

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import pt.rfsfernandes.pocketdex.data.local.PokemonSpeciesTypeConverters

/**
 * Class PokemonSpecies created at 1/17/21 20:00 for the project PoketDex
 * By: rodrigofernandes
 */
@Entity
@TypeConverters(PokemonSpeciesTypeConverters::class)
class PokemonSpecies(
    @PrimaryKey var id: Int,
    @SerializedName("flavor_text_entries") var flavourEntriesList: List<FlavourEntries>
)