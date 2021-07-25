package dev.lucasvillaverde.common.base.domain

import dev.lucasvillaverde.common.base.model.BaseResource
import kotlinx.coroutines.flow.Flow

abstract class BaseFlowableUseCase<Resp, Param> {
    abstract fun execute(params: Param): Flow<BaseResource<Resp>>
}