package pt.rfsfernandes.pocketdex.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pt.rfsfernandes.pocketdex.data.repository.RepositoryImpl
import pt.rfsfernandes.pocketdex.model.Resource
import pt.rfsfernandes.pocketdex.model.pokemon.Pokemon
import pt.rfsfernandes.pocketdex.model.service_responses.PokemonResult

class SplashViewModel(private val mRepositoryImpl: RepositoryImpl): ViewModel() {
    val pokemonMutableLiveData = MutableLiveData<Resource<List<PokemonResult?>?>>()

    fun getPokemons() {
        viewModelScope.launch {
            mRepositoryImpl.getWholePokemonList().collect {
                pokemonMutableLiveData.postValue(it)
            }
        }
    }

}