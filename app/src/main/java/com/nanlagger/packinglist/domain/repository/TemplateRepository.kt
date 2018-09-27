package com.nanlagger.packinglist.domain.repository

import com.nanlagger.packinglist.domain.entities.Template
import io.reactivex.Observable

class TemplateRepository {

    fun getTemplates(): Observable<List<Template>> {
        return Observable.just(emptyList())
    }

    fun deleteTemplate(id: Long): Observable<Boolean> {
        return Observable.just(true)
    }

    fun updateTemplate(template: Template): Observable<Template> {
        return Observable.just(template)
    }
}