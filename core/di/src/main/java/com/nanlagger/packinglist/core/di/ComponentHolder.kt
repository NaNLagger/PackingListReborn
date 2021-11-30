package com.nanlagger.packinglist.core.di

abstract class ComponentHolder<Component, Deps : Dependencies> {

    private val components: MutableMap<String, Component> = mutableMapOf()
    var dependencyProvider: DependencyProvider<Deps>? = null

    protected abstract fun createComponent(key: String, deps: Deps): Component

    fun createOrGetComponent(key: String, parentKey: String = ""): Component {
        return components[key] ?: createComponent(
            key,
            dependencyProvider?.get(parentKey) ?: error("Dependency provider doesn't set")
        ).also { components[key] = it }
    }

    fun getComponent(key: String): Component {
        return components[key] ?: error("Component with key = $key doesn't exist")
    }

    fun getComponentOrNull(key: String): Component? {
        return components[key]
    }

    fun deleteComponent(key: String) {
        components.remove(key)
    }
}