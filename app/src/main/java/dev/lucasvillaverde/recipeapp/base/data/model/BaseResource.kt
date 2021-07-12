package dev.lucasvillaverde.recipeapp.base.data.model

sealed class BaseResource<T>(val data: T? = null) {
    class Loading<T> : BaseResource<T>()
    class Success<T>(data: T) : BaseResource<T>(data)
    class Error<T>(data: T, val errorMessage: String) : BaseResource<T>(data)
}