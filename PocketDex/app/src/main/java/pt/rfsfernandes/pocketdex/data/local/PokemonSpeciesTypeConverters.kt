package pt.rfsfernandes.pocketdex.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pt.rfsfernandes.pocketdex.model.moves.EffectsEntries
import pt.rfsfernandes.pocketdex.model.pokemon_species.FlavourEntries

object PokemonSpeciesTypeConverters {
    // FlavourEntries
    @TypeConverter
    fun stringToPoFlavourEntriesList(data: String?): List<FlavourEntries> {
        if (data == null) {
            return emptyList()
        }
        val listType = object : TypeToken<List<FlavourEntries?>?>() {}.type
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun flavourEntriesToString(someObjects: List<FlavourEntries?>?): String {
        return Gson().toJson(someObjects)
    }

    // EffectsEntries
    @TypeConverter
    fun stringToEffectsEntriesList(data: String?): List<EffectsEntries> {
        if (data == null) {
            return emptyList()
        }
        val listType = object : TypeToken<List<EffectsEntries?>?>() {}.type
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun effectsEntriesToString(someObjects: List<EffectsEntries?>?): String {
        return Gson().toJson(someObjects)
    }
}