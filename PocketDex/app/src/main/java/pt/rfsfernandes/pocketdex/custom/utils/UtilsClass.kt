package pt.rfsfernandes.pocketdex.custom.utils

import android.content.Context
import pt.rfsfernandes.pocketdex.R
import java.util.*

object UtilsClass {
    /**
     * A method to turn pokemon names with hifen (-) into camel cased name
     *
     * @param word to camel case
     * @return String in camel case
     */
    fun toCamelCase(word: String): String {
        val s = word.split("-").toTypedArray()
        val newWord = StringBuilder()
        for (s1 in s) {
            val chars = s1.toCharArray()
            for (i in chars.indices) {
                if (i == 0) newWord.append(
                    chars[i].toString().uppercase(Locale.getDefault())
                ) else newWord.append(chars[i].toString().lowercase(Locale.getDefault()))
            }
            newWord.append(" ")
        }
        newWord.trimToSize()
        return newWord.toString()
    }

    /**
     * Returns the color matching the name
     *
     * @param typeName Pokemon Type
     * @return Color of corresponding pokemon type
     */
    fun returnColorId(context: Context, typeName: String): Int {
        var colorId = 0
        when (typeName.lowercase(Locale.getDefault())) {
            "bug" -> colorId = context.resources.getColor(R.color.bug, null)
            "dark" -> colorId = context.resources.getColor(R.color.dark, null)
            "dragon" -> colorId = context.resources.getColor(R.color.dragon, null)
            "electric" -> colorId = context.resources.getColor(R.color.electric, null)
            "fairy" -> colorId = context.resources.getColor(R.color.fairy, null)
            "fighting" -> colorId = context.resources.getColor(R.color.fighting, null)
            "fire" -> colorId = context.resources.getColor(R.color.fire, null)
            "flying" -> colorId = context.resources.getColor(R.color.flying, null)
            "ghost" -> colorId = context.resources.getColor(R.color.ghost, null)
            "grass" -> colorId = context.resources.getColor(R.color.grass, null)
            "ground" -> colorId = context.resources.getColor(R.color.ground, null)
            "ice" -> colorId = context.resources.getColor(R.color.ice, null)
            "normal" -> colorId = context.resources.getColor(R.color.normal, null)
            "poison" -> colorId = context.resources.getColor(R.color.poison, null)
            "psychic" -> colorId = context.resources.getColor(R.color.psychic, null)
            "rock" -> colorId = context.resources.getColor(R.color.rock, null)
            "steel" -> colorId = context.resources.getColor(R.color.steel, null)
            "water" -> colorId = context.resources.getColor(R.color.water, null)
        }
        return colorId
    }
}