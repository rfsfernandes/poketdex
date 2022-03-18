package pt.rfsfernandes.model.type

import androidx.room.Entity
import androidx.room.TypeConverters
import pt.rfsfernandes.data.local.PokemonTypeConverters
import pt.rfsfernandes.model.SimpleModelData
import com.google.gson.annotations.SerializedName
import pt.rfsfernandes.model.type.DamageRelations
import androidx.room.PrimaryKey

/**
 * Class Type created at 1/19/21 23:50 for the project PoketDex
 * By: rodrigofernandes
 */
@Entity
@TypeConverters(PokemonTypeConverters::class)
class Type(@field:PrimaryKey @field:SerializedName("id") var id: Int, @field:SerializedName("damage_relations") var damageRelations: DamageRelations, @field:SerializedName("name") var name: String)