package dev.lucasvillaverde.common.base.model

sealed class BaseResource<T>(val data: T? = null) {
    class Success<T>(data: T? = null) : BaseResource<T>(data)
    class Error<T>(val errorMessage: String) : BaseResource<T>()
}