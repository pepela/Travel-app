package com.peranidze.remote

interface EntityMapper<in Model, out Entity> {

    fun from(model: Model): Entity

    fun from(models: List<Model>): List<Entity> = MutableList(models.size) { from(models[it]) }

}
