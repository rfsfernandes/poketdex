package pt.rfsfernandes.model.type

import androidx.room.TypeConverters
import pt.rfsfernandes.data.local.PokemonTypeConverters
import pt.rfsfernandes.model.SimpleModelData
import com.google.gson.annotations.SerializedName
import pt.rfsfernandes.model.type.DamageRelations
import androidx.room.PrimaryKey

/**
 * Class DamageRelations created at 1/20/21 01:07 for the project PoketDex
 * By: rodrigofernandes
 */
@TypeConverters(PokemonTypeConverters::class)
data class DamageRelations(
        @SerializedName("double_damage_from") var doubleDamageFrom: List<SimpleModelData>,
        @SerializedName("double_damage_to") var doubleDamageTo: List<SimpleModelData>,
        @SerializedName("half_damage_from") var halfDamageFrom: List<SimpleModelData>,
        @SerializedName("half_damage_to") var halfDamageTo: List<SimpleModelData>,
        @SerializedName("no_damage_from") var noDamageFrom: List<SimpleModelData>,
        @SerializedName("no_damage_to") var noDamageTo: List<SimpleModelData>
) {

    override fun toString(): String {
        return "DamageRelations{" +
                "doubleDamageFrom=" + doubleDamageFrom +
                ", doubleDamageTo=" + doubleDamageTo +
                ", halfDamageFrom=" + halfDamageFrom +
                ", halfDamageTo=" + halfDamageTo +
                ", noDamageFrom=" + noDamageFrom +
                ", noDamageTo=" + noDamageTo +
                '}'
    }
}