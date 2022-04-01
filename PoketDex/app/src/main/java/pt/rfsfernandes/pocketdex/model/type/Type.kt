package pt.rfsfernandes.pocketdex.model.type

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import pt.rfsfernandes.pocketdex.data.local.PokemonTypeConverters

/**
 * Class Type created at 1/19/21 23:50 for the project PoketDex
 * By: rodrigofernandes
 */
@Entity
@TypeConverters(PokemonTypeConverters::class)
class Type(
    @PrimaryKey @SerializedName("id") var id: Int,
    @SerializedName("damage_relations") var damageRelations: DamageRelations,
    @SerializedName("name") var name: String
)