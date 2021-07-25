package dev.lucasvillaverde.common.base.model

data class BasePageState<T>(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val data: T?
)
