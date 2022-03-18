package pt.rfsfernandes.model.service_responses

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class PokemonListResponse(
        @SerializedName("count")
         var count: Int = 0,
        @SerializedName("next")
         var nextPage: String? = null,
        @SerializedName("previous")
         var previousPage: String? = null,
        @SerializedName("results")
         var resultList: List<PokemonResult>? = null

) {

    override fun toString(): String {
        return "PokemonListResponse{" +
                "count=" + count +
                ", nextPage='" + nextPage + '\'' +
                ", previousPage='" + previousPage + '\'' +
                ", resultList=" + resultList +
                '}'
    }
}