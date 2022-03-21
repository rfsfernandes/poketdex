package pt.rfsfernandes.pocketdex.data.remote

import pt.rfsfernandes.pocketdex.custom.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataSource {
    private const val BASE_URL = Constants.BASE_URL
    private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    private var sPokemonService: PokemonService? = null
    val pokemonService: PokemonService?
        get() {
            if (sPokemonService == null) {
                sPokemonService = retrofit.create(PokemonService::class.java)
            }
            return sPokemonService
        }
}