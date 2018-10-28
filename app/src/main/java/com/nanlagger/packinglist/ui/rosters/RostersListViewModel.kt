package com.nanlagger.packinglist.ui.rosters

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.nanlagger.packinglist.domain.entities.Roster
import com.nanlagger.packinglist.domain.interactors.RosterInteractor
import com.nanlagger.packinglist.tools.addTo
import io.reactivex.disposables.CompositeDisposable
import ru.terrakok.cicerone.Router
import timber.log.Timber

class RostersListViewModel(
        private val router: Router,
        private val rosterInteractor: RosterInteractor
) : ViewModel() {

    val rostersList: LiveData<List<Roster>>
        get() = rostersListLiveData

    private val rostersListLiveData: MutableLiveData<List<Roster>> = MutableLiveData()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var firstAttach = true

    fun init() {
        if (firstAttach) {
            loadRosters()
        }
        firstAttach = false
    }

    fun newRoster() {

    }

    fun openRoster(roster: Roster) {

    }

    private fun loadRosters() {
        rosterInteractor.getRosters()
                .subscribe({
                    rostersListLiveData.value = it
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
            return if (RostersListViewModel::class.java.isAssignableFrom(modelClass)) {
                RostersListViewModel(router, interactor) as T
            } else {
                throw RuntimeException("Cannot create an instance of $modelClass")
            }
        }
    }
}