package dev.lucasvillaverde.recipeapp.base.data.model

sealed class BaseResource<T>(val data: T? = null) {
    class Success<T>(data: T?) : BaseResource<T>(data)
    class Error<T>(val errorMessage: String) : BaseResource<T>()
}