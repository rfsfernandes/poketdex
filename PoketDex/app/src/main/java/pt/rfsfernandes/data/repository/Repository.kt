package pt.rfsfernandes.data.repository

import android.util.Log
import pt.rfsfernandes.custom.Constants
import pt.rfsfernandes.data.remote.PokemonService
import pt.rfsfernandes.data.local.PokemonDAO
import pt.rfsfernandes.custom.callbacks.ResponseCallBack
import pt.rfsfernandes.model.service_responses.PokemonResult
import pt.rfsfernandes.model.service_responses.PokemonListResponse
import pt.rfsfernandes.model.pokemon.Pokemon
import pt.rfsfernandes.model.pokemon_species.PokemonSpecies
import pt.rfsfernandes.model.moves.Moves
import pt.rfsfernandes.model.type.Type
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class Repository(private val mPokemonService: PokemonService, private val mPokemonDAO: PokemonDAO) {
    /**
     * Fetches a list of pokemons. If there is a list already in the local database, should return
     * that list. If there isn't, should use API Endpoint
     *
     * @param offset   Where the list begins
     * @param limit    Size of the list to be fetched
     * @param callBack Used when data is ready to be delivered
     */
    fun getPokemonList(offset: Int, limit: Int,
                       callBack: ResponseCallBack<List<PokemonResult>?>) {
        Thread {
            val pokemonResultList = mPokemonDAO.getPokemonsFromOffsetWithLimit(offset + 1, offset + limit)
            if (pokemonResultList != null && pokemonResultList.size != limit) {
                val call = mPokemonService.getPokemonListPagination(offset, limit)
                call.enqueue(object : Callback<PokemonListResponse?> {
                    override fun onResponse(call: Call<PokemonListResponse?>, response: Response<PokemonListResponse?>) {
                        if (response.isSuccessful) {
                            if (response.body() != null) {
                                val pokemonListResponse = response.body()
                                val tempList: MutableList<PokemonResult> = ArrayList()
                                pokemonListResponse?.let { response ->
                                    response.resultList?.let { list ->
                                        for (i in list.indices) {
                                            val pokemonResult: PokemonResult = list[i]
                                            tempList.add(PokemonResult(pokemonResult.name, pokemonResult.url,
                                                    offset + (i + 1),
                                                    Constants.ARTWORK_URL.replace("{pokemonId}", (offset + (i + 1)).toString()), false))
                                        }
                                        Thread {
                                            mPokemonDAO.insertPokemonResults(tempList)
                                            callBack.onSuccess(tempList)
                                        }.start()
                                    }
                                }
                            } else {
                                callBack.onFailure(response.message())
                            }
                        } else {
                            callBack.onFailure(response.message())
                        }
                    }

                    override fun onFailure(call: Call<PokemonListResponse?>, t: Throwable) {
                        callBack.onFailure(t.localizedMessage)
                        Log.e("PokemonListError", t.localizedMessage)
                    }
                })
            } else {
                callBack.onSuccess(pokemonResultList)
            }
        }.start()
    }

    /**
     * Fetches a pokemon by id. If there is a pokemon with that ID already in the local database,
     * should return that pokemon. If there isn't, should use API Endpoint
     *
     * @param id       Pokemon ID
     * @param callBack Used when data is ready to be delivered
     */
    fun getPokemonById(id: Int, callBack: ResponseCallBack<Pokemon?>) {
        Thread {
            val pokemon = mPokemonDAO.getPokemonById(id)
            if (pokemon == null) {
                val call = mPokemonService.getPokemonById(id)
                call.enqueue(object : Callback<Pokemon?> {
                    override fun onResponse(call: Call<Pokemon?>, response: Response<Pokemon?>) {
                        if (response.isSuccessful) {
                            if (response.body() != null) {
                                val pokemon = response.body()
                                Thread { mPokemonDAO.insertPokemon(pokemon) }.start()
                                callBack.onSuccess(pokemon)
                            } else {
                                callBack.onFailure(response.message())
                            }
                        } else {
                            callBack.onFailure(response.message())
                        }
                    }

                    override fun onFailure(call: Call<Pokemon?>, t: Throwable) {
                        callBack.onFailure(t.localizedMessage)
                        Log.e("PokemonByIdError", t.localizedMessage)
                    }
                })
            } else {
                callBack.onSuccess(pokemon)
            }
        }.start()
    }

    /**
     * Fetches a pokemon species by pokemonId. If there is a species with that ID already in the
     * local database, should return that species. If there isn't, should use API Endpoint
     *
     * @param pokemonId Pokemon ID
     * @param callBack  Used when data is ready to be delivered
     */
    fun getPokemonSpeciesById(pokemonId: Int, callBack: ResponseCallBack<PokemonSpecies?>) {
        Thread {
            val pokemonSpecies = mPokemonDAO.getSpeciesByPokemonId(pokemonId)
            if (pokemonSpecies == null) {
                val call = mPokemonService.getPokemonSpeciesById(pokemonId)
                call.enqueue(object : Callback<PokemonSpecies?> {
                    override fun onResponse(call: Call<PokemonSpecies?>, response: Response<PokemonSpecies?>) {
                        if (response.isSuccessful) {
                            if (response.body() != null) {
                                val pokemonSpecies = response.body()
                                pokemonSpecies!!.id = pokemonId
                                Thread { mPokemonDAO.insertSpecies(pokemonSpecies) }.start()
                                callBack.onSuccess(pokemonSpecies)
                            } else {
                                callBack.onFailure(response.message())
                            }
                        } else {
                            callBack.onFailure(response.message())
                        }
                    }

                    override fun onFailure(call: Call<PokemonSpecies?>, t: Throwable) {
                        callBack.onFailure(t.localizedMessage)
                        Log.e("PokemonSpeciesByIdError", t.localizedMessage)
                    }
                })
            } else {
                callBack.onSuccess(pokemonSpecies)
            }
        }.start()
    }

    /**
     * Fetches a move by id. If there is a move with that ID already in the local database,
     * should return that move. If there isn't, should use API Endpoint
     *
     * @param moveId   Move ID
     * @param callBack Used when data is ready to be delivered
     */
    fun getMoveById(moveId: String, callBack: ResponseCallBack<Moves?>) {
        if (!moveId.isEmpty()) {
            Thread {
                val pokemonSpecies = mPokemonDAO.getMovesByMoveId(moveId.toInt())
                if (pokemonSpecies == null) {
                    val call = mPokemonService.getMoveById(moveId.toInt())
                    call.enqueue(object : Callback<Moves?> {
                        override fun onResponse(call: Call<Moves?>, response: Response<Moves?>) {
                            if (response.isSuccessful) {
                                if (response.body() != null) {
                                    val moves = response.body()
                                    Thread { mPokemonDAO.insertMoves(moves) }.start()
                                    callBack.onSuccess(moves)
                                } else {
                                    callBack.onFailure(response.message())
                                }
                            } else {
                                callBack.onFailure(response.message())
                            }
                        }

                        override fun onFailure(call: Call<Moves?>, t: Throwable) {
                            callBack.onFailure(t.localizedMessage)
                            Log.e("PokemonMovesByIdError", t.localizedMessage)
                        }
                    })
                } else {
                    callBack.onSuccess(pokemonSpecies)
                }
            }.start()
        }
    }

    /**
     * Fetches a List of Moves from a List of movesId from the database
     *
     * @param movesIds Moves IDs to fetch
     * @param callBack Used when data is ready to be delivered
     */
    fun getMovesFromIds(movesIds: List<String?>?, callBack: ResponseCallBack<List<Moves?>?>) {
        Thread {
            val movesList = mPokemonDAO.getMovesFromIdList(movesIds)
            if (movesList != null && movesList.size != 0) {
                callBack.onSuccess(movesList)
            } else {
                callBack.onFailure("")
            }
        }.start()
    }

    /**
     * Fetches a Type by id. If there is a Type with that ID already in the local database,
     * should return that Type. If there isn't, should use API Endpoint
     *
     * @param typeId   Type ID
     * @param callBack Used when data is ready to be delivered
     */
    fun getTypeInfoById(typeId: Int, callBack: ResponseCallBack<Type?>) {
        Thread {
            val type = mPokemonDAO.getTypeById(typeId)
            if (type == null) {
                val call = mPokemonService.getTypeInfoById(typeId)
                call.enqueue(object : Callback<Type?> {
                    override fun onResponse(call: Call<Type?>, response: Response<Type?>) {
                        if (response.isSuccessful) {
                            if (response.body() != null) {
                                val typeCall = response.body()
                                Thread { mPokemonDAO.insertType(typeCall) }.start()
                                callBack.onSuccess(typeCall)
                            } else {
                                callBack.onFailure(response.message())
                            }
                        } else {
                            callBack.onFailure(response.message())
                        }
                    }

                    override fun onFailure(call: Call<Type?>, t: Throwable) {
                        callBack.onFailure(t.localizedMessage)
                        Log.e("PokemonTypeByIdError", t.localizedMessage)
                    }
                })
            } else {
                callBack.onSuccess(type)
            }
        }.start()
    }
}