package com.nanlagger.packinglist.features.editName.domain


interface EditNameInteractor {
    fun getInfo(): EditNameInfo
    fun setName(name: String)
}