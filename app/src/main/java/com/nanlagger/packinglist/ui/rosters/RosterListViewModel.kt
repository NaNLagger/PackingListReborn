package com.nanlagger.packinglist.ui.rosters

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.nanlagger.packinglist.domain.entities.Roster
import com.nanlagger.packinglist.domain.interactors.RosterInteractor
import com.nanlagger.packinglist.navigation.Screens
import com.nanlagger.packinglist.tools.addTo
import io.reactivex.disposables.CompositeDisposable
import ru.terrakok.cicerone.Router
import timber.log.Timber

class RosterListViewModel(
        private val router: Router,
        private val rosterInteractor: RosterInteractor
) : ViewModel() {

    val rosterList: LiveData<List<Roster>>
        get() = rosterListLiveData

    private val rosterListLiveData: MutableLiveData<List<Roster>> = MutableLiveData()
    private var rosterItems: MutableList<Roster> = mutableListOf()

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var firstAttach = true

    fun init() {
        if (firstAttach) {
            loadRosters()
        }
        firstAttach = false
    }

    fun newRoster() {
        val rosterIndex = (rosterItems.maxBy { it.priority }?.priority ?: 0) + 1
        rosterInteractor.addRoster(Roster(0, "Roster $rosterIndex", rosterIndex, emptyList()))
                .subscribe({}, { error -> Timber.e(error) })
                .addTo(compositeDisposable)
    }

    fun openRoster(roster: Roster) {
        router.navigateTo(Screens.ROSTER_SCREEN, roster.id)
    }

    fun changePriority(oldPosition: Int, newPosition: Int) {
        val roster = rosterItems[oldPosition]
        rosterItems.removeAt(oldPosition)
        rosterItems.add(newPosition, roster)
        rosterListLiveData.value = rosterItems.toList()
    }

    fun saveOrder() {
        val changedRosters = rosterItems
                .filterIndexed { index, roster ->
                    val nIndex = rosterItems.size - index
                    val isChanged = roster.priority != nIndex
                    if(isChanged) {
                        roster.priority = nIndex
                    }
                    isChanged
                }
        if (changedRosters.isNotEmpty()) {
            rosterInteractor.changePriority(changedRosters)
                    .subscribe({}, { error -> Timber.e(error)})
                    .addTo(compositeDisposable)
        }
    }

    fun deleteRoster(position: Int) {
        rosterInteractor.deleteRoster(rosterItems[position].id)
                .subscribe({}, { error -> Timber.e(error)})
                .addTo(compositeDisposable)
    }

    private fun loadRosters() {
        rosterInteractor.getRosters()
                .subscribe({ rosters ->
                    rosterItems = rosters.asSequence().sortedByDescending { it.priority }.toMutableList()
                    rosterListLiveData.value = rosterItems.toList()
                }, { error -> Timber.e(error)})
                .addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(val router: Router, val interactor: RosterInteractor) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (RosterListViewModel::class.java.isAssignableFrom(modelClass)) {
                RosterListViewModel(router, interactor) as T
            } else {
                throw RuntimeException("Cannot create an instance of $modelClass")
            }
        }
    }
}