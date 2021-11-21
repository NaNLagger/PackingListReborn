package com.nanlagger.packinglist.di

interface DependencyProvider<D: Dependencies> {

    fun get(key: String): D
}