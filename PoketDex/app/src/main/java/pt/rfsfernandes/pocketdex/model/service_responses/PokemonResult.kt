package pt.rfsfernandes.pocketdex.model.service_responses

import androidx.room.Entity
import androidx.room.PrimaryKey
import pt.rfsfernandes.pocketdex.model.SimpleModelData

@Entity
class PokemonResult(
    name: String,
    url: String,
    @PrimaryKey var listPosition: Int,
    var pokemonImage: String,
    var isSelected: Boolean
) : SimpleModelData(name, url) {


    override fun toString(): String {
        return "PokemonResult{" +
                "listPosition=" + listPosition +
                '}'
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PokemonResult

        if (listPosition != other.listPosition) return false
        if (pokemonImage != other.pokemonImage) return false
        if (isSelected != other.isSelected) return false

        return true
    }

    override fun hashCode(): Int {
        var result = listPosition
        result = 31 * result + pokemonImage.hashCode()
        result = 31 * result + isSelected.hashCode()
        return result
    }

}