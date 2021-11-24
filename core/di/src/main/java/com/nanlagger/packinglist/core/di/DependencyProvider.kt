package com.nanlagger.packinglist.core.di

interface DependencyProvider<D: Dependencies> {

    fun get(key: String): D
}