package com.nanlagger.packinglist.features.roster.details.ui

import androidx.lifecycle.*
import com.github.terrakok.cicerone.Router
import com.nanlagger.packinglist.core.common.BaseViewModel
import com.nanlagger.packinglist.features.editName.domain.EditNameInfo
import com.nanlagger.packinglist.features.editName.domain.EditNameInteractorImpl
import com.nanlagger.packinglist.features.roster.domain.entities.Roster
import com.nanlagger.packinglist.features.roster.domain.entities.RosterItem
import com.nanlagger.packinglist.features.roster.domain.interactors.RosterInteractor
import com.nanlagger.packinglist.features.roster.domain.interactors.RosterItemInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class RosterViewModel(
    private val router: Router,
    private val rosterInteractor: RosterInteractor,
    private val rosterItemInteractor: RosterItemInteractor,
    private val editNameInteractor: EditNameInteractorImpl
) : BaseViewModel(router) {

    private var rosterId = 0L
    private val _uiState: MutableStateFlow<Roster> = MutableStateFlow(Roster.EMPTY)

    val uiState: StateFlow<Roster> = _uiState.asStateFlow()

    fun init(id: Long) {
        this.rosterId = id
        onAttach()
    }

    override fun onFirstAttach() {
        loadRoster()
        editNameInteractor.setInfo(EditNameInfo("Roster Item Name"))
        viewModelScope.launch {
            editNameInteractor.observeName()
                .collect { newItem(it) }
        }
    }

    fun checkItem(rosterItem: RosterItem, isChecked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            rosterItemInteractor.update(rosterItem.copy(checked = isChecked))
        }
    }

    fun newItem(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            rosterItemInteractor.addRosterItem(RosterItem(0L, name, rosterId, false))
        }
    }

    private fun loadRoster() {
        viewModelScope.launch {
            rosterInteractor.getRoster(rosterId)
                .flowOn(Dispatchers.IO)
                .catch { Timber.e(it) }
                .collect { _uiState.value = it }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val router: Router,
        private val rosterInteractor: RosterInteractor,
        private val rosterItemInteractor: RosterItemInteractor,
        private val editNameInteractor: EditNameInteractorImpl
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (RosterViewModel::class.java.isAssignableFrom(modelClass)) {
                RosterViewModel(router, rosterInteractor, rosterItemInteractor, editNameInteractor) as T
            } else {
                throw RuntimeException("Cannot create an instance of $modelClass")
            }
        }
    }
}
