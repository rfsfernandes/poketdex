package pt.rfsfernandes.model.pokemon.moves

import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import pt.rfsfernandes.data.local.PokemonTypeConverters
import pt.rfsfernandes.model.SimpleModelData

data class PokemonMoves(
        @TypeConverters(PokemonTypeConverters::class)
        @SerializedName("move") var move: SimpleModelData) {

    override fun toString(): String {
        return "Moves{" +
                "move=" + move +
                '}'
    }
}