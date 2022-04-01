package pt.rfsfernandes.pocketdex.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pt.rfsfernandes.pocketdex.model.SimpleModelData
import pt.rfsfernandes.pocketdex.model.pokemon.moves.PokemonMoves
import pt.rfsfernandes.pocketdex.model.pokemon.sprites.OfficialArtWork
import pt.rfsfernandes.pocketdex.model.pokemon.sprites.OtherSpriteInfo
import pt.rfsfernandes.pocketdex.model.pokemon.sprites.PokemonSprites
import pt.rfsfernandes.pocketdex.model.pokemon.stats.Stats
import pt.rfsfernandes.pocketdex.model.pokemon.types.PokemonType
import pt.rfsfernandes.pocketdex.model.service_responses.PokemonResult
import pt.rfsfernandes.pocketdex.model.type.DamageRelations

object PokemonTypeConverters {
    // Pokemon Result
    @TypeConverter
    fun stringToPokemonResultList(data: String?): List<PokemonResult> {
        if (data == null) {
            return emptyList()
        }
        val listType = object : TypeToken<List<PokemonResult?>?>() {}.type
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun pokemonResultToString(someObjects: List<PokemonResult?>?): String {
        return Gson().toJson(someObjects)
    }

    // SimpleModelData
    @TypeConverter
    fun stringToSimpleModelDataList(data: String?): List<SimpleModelData> {
        if (data == null) {
            return emptyList()
        }
        val listType = object : TypeToken<List<SimpleModelData?>?>() {}.type
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun simpleModelDataListToString(someObjects: List<SimpleModelData?>?): String {
        return Gson().toJson(someObjects)
    }

    @TypeConverter
    fun stringToSimpleModelData(data: String?): SimpleModelData {
        if (data == null) {
            return SimpleModelData("", "")
        }
        val listType = object : TypeToken<SimpleModelData?>() {}.type
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun simpleModelDataToString(someObjects: SimpleModelData?): String {
        return Gson().toJson(someObjects)
    }

    // Moves
    @TypeConverter
    fun stringToMoves(data: String?): List<PokemonMoves> {
        if (data == null) {
            return emptyList()
        }
        val listType = object : TypeToken<List<PokemonMoves?>?>() {}.type
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun movesToString(someObjects: List<PokemonMoves?>?): String {
        return Gson().toJson(someObjects)
    }

    // Stats
    @TypeConverter
    fun stringToStats(data: String?): List<Stats> {
        if (data == null) {
            return emptyList()
        }
        val listType = object : TypeToken<List<Stats?>?>() {}.type
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun statsToString(someObjects: List<Stats?>?): String {
        return Gson().toJson(someObjects)
    }

    // PokemonType
    @TypeConverter
    fun stringToPokemonType(data: String?): List<PokemonType> {
        if (data == null) {
            return emptyList()
        }
        val listType = object : TypeToken<List<PokemonType?>?>() {}.type
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun pokemonTypeToString(someObjects: List<PokemonType?>?): String {
        return Gson().toJson(someObjects)
    }

    // PokemonSprites
    @TypeConverter
    fun stringToPokemonSprites(data: String?): PokemonSprites {
        if (data == null) {
            return PokemonSprites("", "", OtherSpriteInfo(OfficialArtWork("")))
        }
        val listType = object : TypeToken<PokemonSprites?>() {}.type
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun pokemonSpritesToString(someObjects: PokemonSprites?): String {
        return Gson().toJson(someObjects)
    }

    // OtherSpriteInfo
    @TypeConverter
    fun stringToOtherSpriteInfo(data: String?): List<OtherSpriteInfo> {
        if (data == null) {
            return emptyList()
        }
        val listType = object : TypeToken<List<OtherSpriteInfo?>?>() {}.type
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun otherSpriteInfoToString(someObjects: List<OtherSpriteInfo?>?): String {
        return Gson().toJson(someObjects)
    }

    // OfficialArtWork
    @TypeConverter
    fun stringToOfficialArtWork(data: String?): List<OfficialArtWork> {
        if (data == null) {
            return emptyList()
        }
        val listType = object : TypeToken<List<OfficialArtWork?>?>() {}.type
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun officialArtWorkToString(someObjects: List<OfficialArtWork?>?): String {
        return Gson().toJson(someObjects)
    }

    // PokemonSprites
    @TypeConverter
    fun stringToDamageRelations(data: String?): DamageRelations {
        if (data == null) {
            return DamageRelations(
                ArrayList(), ArrayList(), ArrayList(),
                ArrayList(), ArrayList(), ArrayList()
            )
        }
        val listType = object : TypeToken<DamageRelations?>() {}.type
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun damageRelationsToString(someObjects: DamageRelations?): String {
        return Gson().toJson(someObjects)
    }
}