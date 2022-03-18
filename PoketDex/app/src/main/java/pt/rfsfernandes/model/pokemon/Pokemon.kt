package pt.rfsfernandes.model.pokemon

import androidx.room.Entity
import androidx.room.TypeConverters
import pt.rfsfernandes.data.local.PokemonTypeConverters
import pt.rfsfernandes.model.pokemon.moves.PokemonMoves
import pt.rfsfernandes.model.SimpleModelData
import pt.rfsfernandes.model.pokemon.types.PokemonType
import pt.rfsfernandes.model.pokemon.sprites.PokemonSprites
import com.google.gson.annotations.SerializedName
import androidx.room.PrimaryKey
import pt.rfsfernandes.model.pokemon.stats.Stats

/**
 * Class Pokemon created at 1/16/21 14:45 for the project PoketDex
 * By: rodrigofernandes
 */
@Entity
@TypeConverters(PokemonTypeConverters::class)
data class Pokemon(
        @SerializedName("base_experience")
        var baseExperience: Int,
        @SerializedName("height")
        var height: Int,
        @SerializedName("weight")
        var weight: Int,
        @SerializedName("moves")
        var moveslist: List<PokemonMoves>,
        @SerializedName("name")
        var name: String,
        @PrimaryKey
        @SerializedName("id")
        var id: Int,
        @SerializedName("species")
        var speciesInfo: SimpleModelData,
        @SerializedName("stats")
        var statsList: List<Stats>,
        @SerializedName("types")
        var typeList: List<PokemonType>,
        @SerializedName("sprites")
        var sprites: PokemonSprites
) {

    override fun toString(): String {
        return "Pokemon{" +
                "baseExperience=" + baseExperience +
                ", height=" + height +
                ", weight=" + weight +
                ", moveslist=" + moveslist +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", speciesInfo=" + speciesInfo +
                ", statsList=" + statsList +
                ", typeList=" + typeList +
                ", sprites=" + sprites +
                '}'
    }
}