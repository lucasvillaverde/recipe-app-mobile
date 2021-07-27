package dev.lucasvillaverde.common.base.domain

import dev.lucasvillaverde.common.base.model.BaseResource

abstract class BaseUseCase<Resp, Param> {
    abstract suspend fun execute(params: Param): BaseResource<Resp>
}

object Noneba