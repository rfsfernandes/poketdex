package pt.rfsfernandes.pocketdex.custom.callbacks

/**
 * Interface ResponseCallBack created at 1/16/21 15:54 for the project PoketDex
 * By: rodrigofernandes
 */
interface ResponseCallBack<T> {
    fun onSuccess(response: T)
    fun onFailure(errorMessage: String?)
}