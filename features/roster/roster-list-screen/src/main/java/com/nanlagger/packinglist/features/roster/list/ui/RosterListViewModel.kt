package com.nanlagger.packinglist.features.roster.list.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.Router
import com.nanlagger.packinglist.core.common.BaseViewModel
import com.nanlagger.packinglist.features.editName.domain.EditNameInfo
import com.nanlagger.packinglist.features.roster.common.navigation.RosterScreenProvider
import com.nanlagger.packinglist.features.roster.domain.entities.Roster
import com.nanlagger.packinglist.features.roster.domain.interactors.RosterInteractor
import com.nanlagger.packinglist.features.editName.domain.EditNameInteractorImpl
import com.nanlagger.utils.extensions.addTo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class RosterListViewModel(
    private val router: Router,
    private val rosterInteractor: RosterInteractor,
    private val rosterScreenProvider: RosterScreenProvider,
    private val editNameInteractor: EditNameInteractorImpl
) : BaseViewModel(router) {

    val rosterList: LiveData<List<Roster>>
        get() = rosterListLiveData

    private val rosterListLiveData: MutableLiveData<List<Roster>> = MutableLiveData()
    private var rosterItems: MutableList<Roster> = mutableListOf()

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onFirstAttach() {
        loadRosters()
        editNameInteractor.setInfo(EditNameInfo("Roster Name"))
        editNameInteractor.observeName()
            .subscribe { newRoster(it) }
            .addTo(compositeDisposable)
    }

    fun openRoster(roster: Roster) {
        router.navigateTo(rosterScreenProvider.getRosterDetailsScreen(roster.id))
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
                if (isChanged) {
                    roster.priority = nIndex
                }
                isChanged
            }
        if (changedRosters.isNotEmpty()) {
            rosterInteractor.changePriority(changedRosters)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({}, { error -> Timber.e(error) })
                .addTo(compositeDisposable)
        }
    }

    fun deleteRoster(position: Int) {
        rosterInteractor.deleteRoster(rosterItems[position].id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, { error -> Timber.e(error) })
            .addTo(compositeDisposable)
    }

    private fun loadRosters() {
        rosterInteractor.getRosters()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ rosters ->
                rosterItems = rosters.asSequence().sortedByDescending { it.priority }.toMutableList()
                rosterListLiveData.value = rosterItems.toList()
            }, { error -> Timber.e(error) })
            .addTo(compositeDisposable)
    }

    private fun newRoster(name: String) {
        val rosterIndex = (rosterItems.maxByOrNull { it.priority }?.priority ?: 0) + 1
        rosterInteractor.addRoster(Roster(0, name, rosterIndex, emptyList()))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, { error -> Timber.e(error) })
            .addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
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