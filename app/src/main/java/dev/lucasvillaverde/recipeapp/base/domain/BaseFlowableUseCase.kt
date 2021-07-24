package dev.lucasvillaverde.recipeapp.base.domain

import dev.lucasvillaverde.recipeapp.base.data.model.BaseResource
import kotlinx.coroutines.flow.Flow

abstract class BaseFlowableUseCase<Resp, Param> {
    abstract fun execute(params: Param): Flow<BaseResource<Resp>>
}