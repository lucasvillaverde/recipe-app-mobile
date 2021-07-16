package dev.lucasvillaverde.recipeapp.base.domain

import dev.lucasvillaverde.recipeapp.base.data.model.BaseResource

abstract class BaseUseCase<R, P> {
    abstract suspend fun execute(params: P): BaseResource<R>
}

object None