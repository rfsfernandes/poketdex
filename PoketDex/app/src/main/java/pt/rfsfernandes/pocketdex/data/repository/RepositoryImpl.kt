package pt.rfsfernandes.pocketdex.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pt.rfsfernandes.pocketdex.custom.Constants
import pt.rfsfernandes.pocketdex.data.local.PokemonDAO
import pt.rfsfernandes.pocketdex.data.remote.PokemonService
import pt.rfsfernandes.pocketdex.model.Resource
import pt.rfsfernandes.pocketdex.model.moves.Moves
import pt.rfsfernandes.pocketdex.model.pokemon.Pokemon
import pt.rfsfernandes.pocketdex.model.pokemon_species.PokemonSpecies
import pt.rfsfernandes.pocketdex.model.service_responses.PokemonResult
import pt.rfsfernandes.pocketdex.model.type.Type

class RepositoryImpl(
    private val mPokemonService: PokemonService,
    private val mPokemonDAO: PokemonDAO
) : Repository {

    /**
     * Fetches a list of pokemons. If there is a list already in the local database, should return
     * that list. If there isn't, should use API Endpoint
     *
     * @param offset   Where the list begins
     * @param limit    Size of the list to be fetched
     */
    override suspend fun getPokemonList(
        offset: Int,
        limit: Int
    ): Flow<Resource<List<PokemonResult?>?>> =
        flow {

            val pokemonResultList =
                mPokemonDAO.getPokemonsFromOffsetWithLimit(offset + 1, offset + limit)
            pokemonResultList?.let {
                emit(Resource.Success(it))
            }
            if (pokemonResultList != null && pokemonResultList.size != limit) {
                emit(Resource.Loading(isLoading = true))
                try {
                    val serviceResponse = mPokemonService.getPokemonListPagination(offset, limit)
                    serviceResponse?.let { response ->
                        if (response.isSuccessful) {
                            val tempList: MutableList<PokemonResult> = ArrayList()
                            response.body()?.let { body ->
                                body.resultList?.let { list ->
                                    for (i in list.indices) {
                                        val pokemonResult: PokemonResult = list[i]
                                        tempList.add(
                                            PokemonResult(
                                                pokemonResult.name, pokemonResult.url,
                                                offset + (i + 1),
                                                Constants.ARTWORK_URL.replace(
                                                    "{pokemonId}",
                                                    (offset + (i + 1)).toString()
                                                ), false
                                            )
                                        )
                                    }
                                    mPokemonDAO.insertPokemonResults(tempList)
                                    emit(
                                        Resource.Success(
                                            mPokemonDAO.getPokemonsFromOffsetWithLimit(
                                                offset + 1,
                                                offset + limit
                                            )
                                        )
                                    )
                                }
                            }
                        } else {
                            emit(Resource.Error(response.message(), pokemonResultList))
                        }
                    }

                } catch (e: Exception) {
                    emit(Resource.NetworkError(e.localizedMessage))
                }

            }
        }

    /**
     * Fetches a pokemon by id. If there is a pokemon with that ID already in the local database,
     * should return that pokemon. If there isn't, should use API Endpoint
     *
     * @param id       Pokemon ID
     */
    override suspend fun getPokemonById(id: Int): Flow<Resource<Pokemon?>> = flow {
        val pokemon = mPokemonDAO.getPokemonById(id)
        pokemon?.let {
            emit(Resource.Success(pokemon))
        }
        if (pokemon == null) {
            emit(Resource.Loading(false))
            try {
                val serviceResponse = mPokemonService.getPokemonById(id)
                serviceResponse?.let { response ->
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            val pokemon = response.body()
                            mPokemonDAO.insertPokemon(pokemon)
                            emit(Resource.Success(mPokemonDAO.getPokemonById(id)))

                        } else {
                            emit(Resource.Error(response.message()))
                        }
                    } else {
                        emit(Resource.Error(response.message()))
                    }
                }
            } catch (e: Exception) {
                emit(Resource.NetworkError(e.localizedMessage))
            }
        }
    }

    /**
     * Fetches a pokemon species by pokemonId. If there is a species with that ID already in the
     * local database, should return that species. If there isn't, should use API Endpoint
     *
     * @param pokemonId Pokemon ID
     */
    override suspend fun getPokemonSpeciesById(pokemonId: Int): Flow<Resource<PokemonSpecies?>> =
        flow {
            val pokemonSpecies = mPokemonDAO.getSpeciesByPokemonId(pokemonId)
            pokemonSpecies?.let {
                emit(Resource.Success(it))
            }
            if (pokemonSpecies == null) {
                emit(Resource.Loading(isLoading = true))
                try {
                    val serviceResponse = mPokemonService.getPokemonSpeciesById(pokemonId)
                    serviceResponse?.let { response ->
                        if (response.isSuccessful) {
                            if (response.body() != null) {
                                val tempPokemonSpecies = response.body()
                                tempPokemonSpecies!!.id = pokemonId
                                mPokemonDAO.insertSpecies(tempPokemonSpecies.apply {
                                    id = pokemonId
                                })
                                emit(Resource.Success(mPokemonDAO.getSpeciesByPokemonId(pokemonId)))
                            } else {
                                emit(Resource.Error(response.message()))
                            }
                        } else {
                            emit(Resource.Error(response.message()))
                        }
                    }

                } catch (e: Exception) {
                    emit(Resource.Error(e.localizedMessage))
                }
            }
        }

    /**
     * Fetches a move by id. If there is a move with that ID already in the local database,
     * should return that move. If there isn't, should use API Endpoint
     *
     * @param moveId   Move ID
     */
    override suspend fun getMoveById(moveId: String): Flow<Resource<Moves?>> = flow {
        if (moveId.isNotEmpty()) {
            val pokemonSpecies = mPokemonDAO.getMovesByMoveId(moveId.toInt())
            if (pokemonSpecies == null) {
                try {
                    val serviceResponse = mPokemonService.getMoveById(moveId.toInt())
                    serviceResponse?.let { response ->
                        if (response.isSuccessful) {
                            if (response.body() != null) {
                                val moves = response.body()
                                mPokemonDAO.insertMoves(moves)
                                emit(Resource.Success(mPokemonDAO.getMovesByMoveId(moveId.toInt())))
                            } else {
                                emit(Resource.Error(response.message()))
                            }
                        } else {
                            emit(Resource.Error(response.message()))
                        }
                    }
                } catch (e: Exception) {
                    emit(Resource.NetworkError(e.localizedMessage))
                }

            }
        }
    }

    /**
     * Fetches a List of Moves from a List of movesId from the database
     *
     * @param movesIds Moves IDs to fetch
     */
    override suspend fun getMovesFromIds(movesIds: List<String?>?): Flow<Resource<List<Moves?>?>> =
        flow {
            val movesList = mPokemonDAO.getMovesFromIdList(movesIds)
            if (movesList != null && movesList.isNotEmpty()) {
                emit(Resource.Success(movesList))
            } else {
                movesIds?.forEach {
                    getMoveById(it.toString())
                }
                emit(Resource.Success(mPokemonDAO.getMovesFromIdList(movesIds)))
            }
        }


    /**
     * Fetches a Type by id. If there is a Type with that ID already in the local database,
     * should return that Type. If there isn't, should use API Endpoint
     *
     * @param typeId   Type ID
     */
    override suspend fun getTypeInfoById(typeId: Int): Flow<Resource<Type?>> = flow {
        val type = mPokemonDAO.getTypeById(typeId)

        if (type == null) {

            try {
                val serviceResponse = mPokemonService.getTypeInfoById(typeId)
                serviceResponse?.let { response ->
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            val typeCall = response.body()
                            mPokemonDAO.insertType(typeCall)
                            emit(Resource.Success(mPokemonDAO.getTypeById(typeId)))
                        } else {
                            emit(Resource.Error(response.message()))
                        }
                    } else {
                        emit(Resource.Error(response.message()))
                    }
                }

            } catch (e: Exception) {
                emit(Resource.NetworkError(e.localizedMessage))
            }
        }
    }
}