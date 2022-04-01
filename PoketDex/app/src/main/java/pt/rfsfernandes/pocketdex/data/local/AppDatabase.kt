package pt.rfsfernandes.pocketdex.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pt.rfsfernandes.pocketdex.model.moves.Moves
import pt.rfsfernandes.pocketdex.model.pokemon.Pokemon
import pt.rfsfernandes.pocketdex.model.pokemon_species.PokemonSpecies
import pt.rfsfernandes.pocketdex.model.service_responses.PokemonResult
import pt.rfsfernandes.pocketdex.model.type.Type

@Database(
    entities = [PokemonResult::class, Pokemon::class, PokemonSpecies::class, Moves::class, Type::class],
    version = DBContract.DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val pokemonDAO: PokemonDAO?

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = buildDatabase(context)
                    }
                }
            }
            return INSTANCE
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DBContract.DATABASE_NAME
            )
                .addCallback(object : Callback() {
                })
                .build()
        }
    }
}