package com.nanlagger.packinglist.features.editName.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nanlagger.packinglist.features.editName.domain.EditNameInfo
import com.nanlagger.packinglist.features.editName.domain.EditNameInteractor
import kotlinx.coroutines.flow.*

class EditNameViewModel(
    private val editNameInteractor: EditNameInteractor
) : ViewModel() {

    val uiState: StateFlow<EditNameInfo> = flowOf(editNameInteractor.getInfo())
        .stateIn(viewModelScope, SharingStarted.Lazily, EditNameInfo(""))

    fun saveName(name: String) {
        editNameInteractor.setName(name)
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val editNameInteractor: EditNameInteractor
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (EditNameViewModel::class.java.isAssignableFrom(modelClass)) {
                EditNameViewModel(editNameInteractor) as T
            } else {
                throw RuntimeException("Cannot create an instance of $modelClass")
            }
        }
    }
}