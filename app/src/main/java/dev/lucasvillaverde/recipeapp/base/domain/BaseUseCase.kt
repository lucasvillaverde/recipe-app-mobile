package dev.lucasvillaverde.recipeapp.base.domain

import dev.lucasvillaverde.recipeapp.base.data.model.BaseResource

abstract class BaseUseCase<Resp, Param> {
    abstract suspend fun execute(params: Param): BaseResource<Resp>
}

object None