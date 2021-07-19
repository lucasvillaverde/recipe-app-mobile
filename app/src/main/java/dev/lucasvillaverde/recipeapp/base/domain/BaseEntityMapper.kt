package dev.lucasvillaverde.recipeapp.base.domain

interface BaseEntityMapper<Entity, DomainModel> {
    fun mapFromEntity(entity: Entity): DomainModel
    fun mapToEntity(domainModel: DomainModel): Entity
}