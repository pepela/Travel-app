package com.peranidze.remote

interface EntityMapper<Model, Entity> {

    fun from(model: Model): Entity

    fun toModel(entity: Entity): Model

    fun from(models: List<Model>): List<Entity> = MutableList(models.size) { from(models[it]) }

    fun toModels(entities: List<Entity>): List<Model> = MutableList(entities.size) { toModel(entities[it]) }

}
