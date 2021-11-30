package com.nanlagger.packinglist.features.editName.di

import com.nanlagger.packinglist.core.di.Dependencies
import com.nanlagger.packinglist.features.editName.domain.EditNameInteractor

interface EditNameDeps : Dependencies {
    val editNameInteractor: EditNameInteractor
}