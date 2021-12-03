package com.nanlagger.packinglist.features.editName.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.runBlocking

class EditNameInteractorImpl : EditNameInteractor {

    private val nameSubject: MutableSharedFlow<String> = MutableSharedFlow()
    private var editNameInfo: EditNameInfo = EditNameInfo("")

    override fun getInfo(): EditNameInfo {
        return editNameInfo
    }

    override fun setName(name: String) {
        runBlocking { nameSubject.emit(name) }
    }

    fun setInfo(info: EditNameInfo) {
        editNameInfo = info
    }

    fun observeName(): Flow<String> {
        return nameSubject.asSharedFlow()
    }

}