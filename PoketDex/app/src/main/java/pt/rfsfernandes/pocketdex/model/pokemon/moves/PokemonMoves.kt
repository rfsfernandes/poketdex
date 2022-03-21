package pt.rfsfernandes.pocketdex.model.pokemon.moves

import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import pt.rfsfernandes.pocketdex.data.local.PokemonTypeConverters
import pt.rfsfernandes.pocketdex.model.SimpleModelData

data class PokemonMoves(
        @TypeConverters(PokemonTypeConverters::class)
        @SerializedName("move") var move: SimpleModelData) {

    override fun toString(): String {
        return "Moves{" +
                "move=" + move +
                '}'
    }
}