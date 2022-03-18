package pt.rfsfernandes.model.moves

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import androidx.room.TypeConverters
import pt.rfsfernandes.data.local.PokemonSpeciesTypeConverters
import pt.rfsfernandes.data.local.PokemonTypeConverters
import pt.rfsfernandes.model.moves.EffectsEntries
import pt.rfsfernandes.model.pokemon_species.FlavourEntries
import pt.rfsfernandes.model.SimpleModelData
import androidx.room.PrimaryKey

/**
 * Class Moves created at 1/19/21 16:51 for the project PoketDex
 * By: rodrigofernandes
 */
@Entity
@TypeConverters(PokemonSpeciesTypeConverters::class, PokemonTypeConverters::class)
data class Moves(
        @PrimaryKey @SerializedName("id") var id: Int,
        @SerializedName("effect_entries") var effectsEntriesList: List<EffectsEntries>,
        @SerializedName("flavor_text_entries") var flavourEntriesList: List<FlavourEntries>,
        @SerializedName("name") var name: String,
        @SerializedName("power") var power: Int,
        @SerializedName("type") var type: SimpleModelData,
        @SerializedName("effect_chance") var effectChance: Int) {

    val effectEntry: EffectsEntries
        get() = effectsEntriesList[0]
    val flavourEntry: FlavourEntries
        get() = flavourEntriesList[0]

    override fun toString(): String {
        return "Moves{" +
                "id=" + id +
                ", mEffectsEntriesList=" + effectsEntriesList +
                ", mFlavourEntriesList=" + flavourEntriesList +
                ", name='" + name + '\'' +
                ", power=" + power +
                ", type=" + type +
                '}'
    }
}