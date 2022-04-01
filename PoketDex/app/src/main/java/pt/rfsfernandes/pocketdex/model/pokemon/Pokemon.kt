package pt.rfsfernandes.pocketdex.model.pokemon

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import pt.rfsfernandes.pocketdex.data.local.PokemonTypeConverters
import pt.rfsfernandes.pocketdex.model.SimpleModelData
import pt.rfsfernandes.pocketdex.model.pokemon.moves.PokemonMoves
import pt.rfsfernandes.pocketdex.model.pokemon.sprites.PokemonSprites
import pt.rfsfernandes.pocketdex.model.pokemon.stats.Stats
import pt.rfsfernandes.pocketdex.model.pokemon.types.PokemonType

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