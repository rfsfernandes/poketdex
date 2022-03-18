package pt.rfsfernandes.model.pokemon_species

import androidx.room.Entity
import androidx.room.TypeConverters
import pt.rfsfernandes.data.local.PokemonTypeConverters
import pt.rfsfernandes.model.SimpleModelData
import com.google.gson.annotations.SerializedName
import pt.rfsfernandes.data.local.PokemonSpeciesTypeConverters
import pt.rfsfernandes.model.pokemon_species.FlavourEntries
import androidx.room.PrimaryKey

/**
 * Class PokemonSpecies created at 1/17/21 20:00 for the project PoketDex
 * By: rodrigofernandes
 */
@Entity
@TypeConverters(PokemonSpeciesTypeConverters::class)
class PokemonSpecies(@field:PrimaryKey var id: Int, @field:SerializedName("flavor_text_entries") var flavourEntriesList: List<FlavourEntries>)