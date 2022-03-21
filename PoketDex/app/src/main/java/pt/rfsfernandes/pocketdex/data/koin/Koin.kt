package pt.rfsfernandes.pocketdex.data.koin

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pt.rfsfernandes.pocketdex.data.local.AppDatabase
import pt.rfsfernandes.pocketdex.data.remote.DataSource
import pt.rfsfernandes.pocketdex.data.repository.RepositoryImpl
import pt.rfsfernandes.pocketdex.viewmodels.MainViewModel

object Koin {
    private val viewModelModule = module {
        viewModel { MainViewModel(get()) }
        single { DataSource.pokemonService }
        single { AppDatabase.getInstance(androidApplication()) }
        single { get<AppDatabase>().pokemonDAO }
        single { RepositoryImpl(get(), get()) }
    }

    val modules = listOf(
            viewModelModule
    )
}