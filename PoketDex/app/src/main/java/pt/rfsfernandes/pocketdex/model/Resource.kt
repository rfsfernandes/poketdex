package pt.rfsfernandes.pocketdex.model

typealias SimpleResource = Resource<Unit>

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T? = null) : Resource<T>(data)
    class Loading<T>(isLoading: Boolean? = false) : Resource<T>()
    class Error<T>(message: String? = null, data: T? = null) : Resource<T>(data, message)
    class NetworkError<T>(message: String?) : Resource<T>()
    class InitialState<T> : Resource<T>()
}