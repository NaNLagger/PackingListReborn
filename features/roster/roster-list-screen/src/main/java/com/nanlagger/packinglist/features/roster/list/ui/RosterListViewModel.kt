package com.nanlagger.packinglist.features.roster.list.ui

import androidx.lifecycle.*
import com.github.terrakok.cicerone.Router
import com.nanlagger.packinglist.core.common.BaseViewModel
import com.nanlagger.packinglist.features.editName.domain.EditNameInfo
import com.nanlagger.packinglist.features.editName.domain.EditNameInteractorImpl
import com.nanlagger.packinglist.features.roster.common.navigation.RosterScreenProvider
import com.nanlagger.packinglist.features.roster.domain.entities.Roster
import com.nanlagger.packinglist.features.roster.domain.interactors.RosterInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class RosterListViewModel(
    private val router: Router,
    private val rosterInteractor: RosterInteractor,
    private val rosterScreenProvider: RosterScreenProvider,
    private val editNameInteractor: EditNameInteractorImpl
) : BaseViewModel(router) {

    val uiState: StateFlow<List<Roster>>
        get() = _uiState.asStateFlow()

    private val _uiState: MutableStateFlow<List<Roster>> = MutableStateFlow(mutableListOf())

    override fun onFirstAttach() {
        loadRosters()
        editNameInteractor.setInfo(EditNameInfo("Roster Name"))
        viewModelScope.launch {
            editNameInteractor.observeName()
                .collect { newRoster(it) }
        }
    }

    fun openRoster(roster: Roster) {
        router.navigateTo(rosterScreenProvider.getRosterDetailsScreen(roster.id))
    }

    fun changePriority(oldPosition: Int, newPosition: Int) {
        val rosterItems = _uiState.value.toMutableList()
        val roster = rosterItems[oldPosition]
        rosterItems.removeAt(oldPosition)
        rosterItems.add(newPosition, roster)
        _uiState.value = rosterItems.toList()
    }

    fun saveOrder() {
        val rosterItems = _uiState.value
        val changedRosters = rosterItems
            .filterIndexed { index, roster ->
                val nIndex = rosterItems.size - index
                val isChanged = roster.priority != nIndex
                if (isChanged) {
                    roster.priority = nIndex
                }
                isChanged
            }
        if (changedRosters.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                rosterInteractor.changePriority(changedRosters)
            }
        }
    }

    fun deleteRoster(position: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val rosterItems = _uiState.value
            rosterInteractor.deleteRoster(rosterItems[position].id)
        }
    }

    private fun loadRosters() {
        viewModelScope.launch {
            rosterInteractor.getRosters()
                .flowOn(Dispatchers.IO)
                .catch { error -> Timber.e(error) }
                .collect { rosters ->
                    _uiState.value = rosters.asSequence().sortedByDescending { it.priority }.toList()
                }
        }
    }

    private fun newRoster(name: String) {
        val rosterItems = _uiState.value
        val rosterIndex = (rosterItems.maxByOrNull { it.priority }?.priority ?: 0) + 1
        viewModelScope.launch(Dispatchers.IO) {
            rosterInteractor.addRoster(Roster(0, name, rosterIndex, emptyList()))
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val router: Router,
        private val interactor: RosterInteractor,
        private val rosterScreenProvider: RosterScreenProvider,
        private val editNameInteractorImpl: EditNameInteractorImpl
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (RosterListViewModel::class.java.isAssignableFrom(modelClass)) {
                RosterListViewModel(router, interactor, rosterScreenProvider, editNameInteractorImpl) as T
            } else {
                throw RuntimeException("Cannot create an instance of $modelClass")
            }
        }
    }
}