package pt.rfsfernandes.data.local;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import pt.rfsfernandes.model.moves.Moves;
import pt.rfsfernandes.model.pokemon.Pokemon;
import pt.rfsfernandes.model.pokemon_species.PokemonSpecies;
import pt.rfsfernandes.model.service_responses.PokemonResult;

@Database(entities = {PokemonResult.class, Pokemon.class, PokemonSpecies.class, Moves.class},
    version = DBContract.DATABASE_VERSION,
    exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

  private static AppDatabase INSTANCE;

  public static AppDatabase getInstance(final Context context) {
    if (INSTANCE == null) {
      synchronized (AppDatabase.class) {
        if (INSTANCE == null) {
          INSTANCE = buildDatabase(context);
        }
      }
    }
    return INSTANCE;
  }

  private static AppDatabase buildDatabase(final Context context) {
    return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DBContract.DATABASE_NAME)
        .addCallback(new Callback() {
          @Override
          public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
          }
        })
        .build();
  }

  public abstract PokemonDAO getPokemonDAO();

}
