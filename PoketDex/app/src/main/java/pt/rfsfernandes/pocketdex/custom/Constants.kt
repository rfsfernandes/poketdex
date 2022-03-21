package pt.rfsfernandes.pocketdex.custom

object Constants {
    const val BASE_URL = "https://pokeapi.co/api/v2/"
    const val ARTWORK_URL = "https://raw.githubusercontent" +
            ".com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/{pokemonId}.png"
    const val RESULT_LIMIT = 50

    enum class MOVES_ITEM {
        SIMPLE, TYPE
    }

    enum class SHOW_TYPE {
        POKEMON, MOVE
    }
}