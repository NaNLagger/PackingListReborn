package com.nanlagger.packinglist.features.editName.domain

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class EditNameInteractorImpl : EditNameInteractor {

    private val nameSubject: PublishSubject<String> = PublishSubject.create()
    private var editNameInfo: EditNameInfo = EditNameInfo("")

    override fun getInfo(): EditNameInfo {
        return editNameInfo
    }

    override fun setName(name: String) {
        nameSubject.onNext(name)
    }

    fun setInfo(info: EditNameInfo) {
        editNameInfo = info
    }

    fun observeName(): Observable<String> {
        return nameSubject.hide()
    }

}