package pt.rfsfernandes.pocketdex.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pt.rfsfernandes.pocketdex.R
import pt.rfsfernandes.pocketdex.custom.Constants
import pt.rfsfernandes.pocketdex.custom.Constants.SHOW_TYPE
import pt.rfsfernandes.pocketdex.data.repository.RepositoryImpl
import pt.rfsfernandes.pocketdex.model.Resource
import pt.rfsfernandes.pocketdex.model.moves.Moves
import pt.rfsfernandes.pocketdex.model.pokemon.Pokemon
import pt.rfsfernandes.pocketdex.model.pokemon.moves.PokemonMoves
import pt.rfsfernandes.pocketdex.model.service_responses.PokemonResult
import pt.rfsfernandes.pocketdex.model.type.Type
import java.util.*

class MainViewModel(private val mRepositoryImpl: RepositoryImpl) : ViewModel() {
    val pokemonListResponseMutableLiveData = MutableLiveData<MutableList<PokemonResult?>?>()
    val fetchErrorLiveData = MutableLiveData<String>()
    val pokemonMutableLiveData = MutableLiveData<Pokemon>()
    val isLoadingMutableLiveData = MutableLiveData<Boolean>()
    val pokemonDescriptionLiveData = MutableLiveData<String>()
    val detailsPagerLiveData = MutableLiveData<Int>()
    val detailsTitleLiveData = MutableLiveData<String>()
    val selectedPokemonId = MutableLiveData<Int>()
    val movesInfo = MutableLiveData<List<Moves?>>()
    val typeMutableLiveData = MutableLiveData<Type>()
    val sHOW_typeMutableLiveData = MutableLiveData<SHOW_TYPE>()
    private var currentOffset = 0

    /**
     * Fetches a list of Pokemons with an offset and a limit
     */
    fun getPokemonList() {
        viewModelScope.launch {
            mRepositoryImpl.getPokemonList(currentOffset, Constants.RESULT_LIMIT).collect {
                when (it) {
                    is Resource.Success -> {
                        val resourceResponse = it.data as MutableList
                        resourceResponse.let { response ->
                            if (pokemonListResponseMutableLiveData.value != null) {
                                val tempPokemonList = pokemonListResponseMutableLiveData.value
                                tempPokemonList!!.removeAt(tempPokemonList.size - 1)
                                tempPokemonList.addAll(response)
                                tempPokemonList.add(null)
                                pokemonListResponseMutableLiveData.postValue(tempPokemonList)
                            } else {
                                response.add(null)
                                pokemonListResponseMutableLiveData.postValue(response)
                            }
                            if (response.size > 0) {
                                currentOffset += Constants.RESULT_LIMIT
                            }
                        }
                    }
                    is Resource.Error -> {
                        it.message?.let {
                            fetchErrorLiveData.postValue(it)
                        }

                    }
                    is Resource.NetworkError -> {
                        it.message?.let {
                            fetchErrorLiveData.postValue(it)
                        }
                    }
                }
            }
        }
    }

    /**
     * Fetches a Pokemon by it's ID
     *
     * @param pokemonId Pokemon ID
     */
    fun pokemonById(pokemonId: Int) {
        selectedPokemonId.postValue(pokemonId)
        pokemonSpeciesById(pokemonId)
        viewModelScope.launch {
            mRepositoryImpl.getPokemonById(pokemonId).collect {
                when (it) {
                    is Resource.Success -> {
                        val resourceResponse = it.data
                        resourceResponse?.let { response ->
                            pokemonMutableLiveData.postValue(response)
                        }
                    }
                    is Resource.Error -> {
                        it.message?.let {
                            fetchErrorLiveData.postValue(it)
                        }

                    }
                    is Resource.NetworkError -> {
                        it.message?.let {
                            fetchErrorLiveData.postValue(it)
                        }
                    }
                }
            }
        }
    }

    /**
     * Fetches a Species of a Pokemon by it's ID
     *
     * @param pokemonId Pokemon ID
     */
    fun pokemonSpeciesById(pokemonId: Int) {
        viewModelScope.launch {
            mRepositoryImpl.getPokemonSpeciesById(pokemonId).collect {
                when (it) {
                    is Resource.Success -> {
                        val resourceResponse = it.data
                        resourceResponse?.let { response ->
                            var chosenFlavour = ""
                            val country = Locale.getDefault().language.split("-")
                                .toTypedArray()[0].lowercase()
                            for (flavour in response.flavourEntriesList) {
                                if (flavour.language.name.contains(country)) {
                                    chosenFlavour = flavour.getFlavourText()
                                    break
                                }
                            }
                            if (chosenFlavour.isEmpty()) {
                                for (flavour in response.flavourEntriesList) {
                                    if (flavour.language.name.contains("en")) {
                                        chosenFlavour = flavour.getFlavourText()
                                        break
                                    }
                                }
                            }
                            pokemonDescriptionLiveData.postValue(chosenFlavour)
                        }
                    }
                    is Resource.Error -> {
                        it.message?.let {
                            fetchErrorLiveData.postValue(it)
                        }

                    }
                    is Resource.NetworkError -> {
                        it.message?.let {
                            fetchErrorLiveData.postValue(it)
                        }
                    }
                }
            }
        }
    }

    /**
     * Notifies that a view should be loading
     *
     * @param isLoading If it's loading or not
     */
    fun isLoading(isLoading: Boolean) {
        isLoadingMutableLiveData.postValue(isLoading)
    }

    /**
     * Deselect all PokemonResults from a list
     */
    fun deselectAll() {
        if (pokemonListResponseMutableLiveData.value != null) {
            val pokemonResults = pokemonListResponseMutableLiveData.value
            if (pokemonResults != null) {
                for (results in pokemonResults) {
                    if (results != null) {
                        results.isSelected = false
                    }
                }
            }
            pokemonListResponseMutableLiveData.postValue(pokemonResults)
        }
    }

    /**
     * Selects an item from a list
     *
     * @param listId Item ID to select
     */
    fun setSelected(listId: Int) {
        selectedPokemonId.postValue(listId)
        val pokemonResults = pokemonListResponseMutableLiveData.value
        if (pokemonResults != null) {
            for (results in pokemonResults) {
                if (results != null) {
                    results.isSelected = results.listPosition == listId
                }
            }
        }
        pokemonListResponseMutableLiveData.postValue(pokemonResults)
    }

    /**
     * Notifies that a ViewPager has changed his page
     *
     * @param page    Page to change to
     * @param wasTurn If the Page was scrolled or forced to change
     */
    fun changePage(page: Int, wasTurn: Boolean, context: Context) {
        var title = ""
        when (page) {

            0 -> title = context.resources.getString(R.string.general_info)
            1 -> title = context.resources.getString(R.string.stats)
            2 -> title = context.resources.getString(R.string.moves)
        }
        detailsTitleLiveData.postValue(title)
        detailsPagerLiveData.postValue(page)
    }

    /**
     * Fetches a list of Moves
     *
     * @param pokemonMoves List of PokemonMoves to get their ID's
     */
    fun getMovesFromIds(pokemonMoves: List<PokemonMoves?>) {

        val movesIds: MutableList<String?> = ArrayList()
        for (pokeMoves in pokemonMoves) {
            movesIds.add(pokeMoves?.move?.urlId)
        }
        viewModelScope.launch {
            mRepositoryImpl.getMovesFromIds(movesIds).collect {
                when (it) {
                    is Resource.Success -> {
                        val resourceResponse = it.data
                        resourceResponse?.let { response ->
                            movesInfo.postValue(response)
                        }
                    }
                    is Resource.Error -> {
                        it.message?.let {
                            fetchErrorLiveData.postValue(it)
                        }

                    }
                    is Resource.NetworkError -> {
                        it.message?.let {
                            fetchErrorLiveData.postValue(it)
                        }
                    }
                }
            }
        }

    }

/*    */
    /**
     * Fetches a list of Moves from the endpoint
     *
     * @param movesId List of move ID's
     *//*
    private fun getMovesFromIdsAPI(movesId: List<String?>) {
        Thread {
            val movesList: MutableList<Moves> = ArrayList()
            val counter = intArrayOf(0)
            for (moveId in movesId) {
                mRepositoryImpl.getMoveById(moveId!!, object : ResponseCallBack<Moves?> {
                    override fun onSuccess(response: Moves) {
                        counter[0]++
                        movesList.add(response)
                        if (movesList.size == counter[0]) {
                            movesInfo.postValue(movesList)
                        }
                    }

                    override fun onFailure(errorMessage: String) {}
                })
            }
        }.start()
    }*/

    /**
     * Get's counters and weaknesses from a type
     *
     * @param typeId    Type to get counters and weaknesses
     * @param show_type If the method was called in order to get counters or weaknesses
     */
    fun getTypeAndCounters(typeId: Int, show_type: SHOW_TYPE) {
        viewModelScope.launch {
            mRepositoryImpl.getTypeInfoById(typeId).collect {
                when (it) {
                    is Resource.Success -> {
                        val resourceResponse = it.data
                        resourceResponse?.let { response ->
                            typeMutableLiveData.postValue(response)
                            sHOW_typeMutableLiveData.postValue(show_type)
                        }
                    }
                    is Resource.Error -> {
                        it.message?.let {
                            fetchErrorLiveData.postValue(it)
                        }

                    }
                    is Resource.NetworkError -> {
                        it.message?.let {
                            fetchErrorLiveData.postValue(it)
                        }
                    }
                }
            }
        }
    }

}