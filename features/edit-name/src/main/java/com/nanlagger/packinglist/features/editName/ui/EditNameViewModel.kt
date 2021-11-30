package com.nanlagger.packinglist.features.editName.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.Router
import com.nanlagger.packinglist.core.common.BaseViewModel
import com.nanlagger.packinglist.features.editName.domain.EditNameInfo
import com.nanlagger.packinglist.features.editName.domain.EditNameInteractor

class EditNameViewModel(
    private val editNameInteractor: EditNameInteractor
) : ViewModel() {

    val info: LiveData<EditNameInfo>
        get() = infoMutableLiveData

    private val infoMutableLiveData: MutableLiveData<EditNameInfo> = MutableLiveData()

    fun onAttach() {
        infoMutableLiveData.value = editNameInteractor.getInfo()
    }

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