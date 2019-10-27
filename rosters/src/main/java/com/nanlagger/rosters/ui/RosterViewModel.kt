package com.nanlagger.rosters.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.nanlagger.rosters.domain.entities.Roster
import com.nanlagger.rosters.domain.entities.RosterItem
import com.nanlagger.rosters.domain.interactors.RosterInteractor
import com.nanlagger.rosters.domain.interactors.RosterItemInteractor
import com.nanlagger.utils.addTo
import io.reactivex.disposables.CompositeDisposable
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

class RosterViewModel(
        private val router: Router,
        private val rosterInteractor: RosterInteractor,
        private val rosterItemInteractor: RosterItemInteractor
) : ViewModel() {

    val rosterUI: LiveData<Roster>
        get() = rosterLiveData

    private val compositeDisposable = CompositeDisposable()
    private var rosterId = 0L
    private var roster: Roster = Roster.EMPTY
    private val rosterLiveData: MutableLiveData<Roster> = MutableLiveData()

    fun init(id: Long) {
        if (rosterId != id) {
            this.rosterId = id
            loadRoster()
        }
    }

    fun back() {
        router.exit()
    }

    fun checkItem(rosterItem: RosterItem, isChecked: Boolean) {
        rosterItemInteractor.updateRosterItem(rosterItem.copy(checked = isChecked))
                .subscribe({}, { error -> Timber.e(error) })
                .addTo(compositeDisposable)
    }

    fun editItem(rosterItem: RosterItem, newTitle: String) {
        rosterItemInteractor.updateRosterItem(rosterItem.copy(name = newTitle))
                .subscribe({}, { error -> Timber.e(error) })
                .addTo(compositeDisposable)
    }

    fun newItem() {
        rosterItemInteractor.addRosterItem(RosterItem(0L, "", rosterId, false))
                .subscribe({}, { error -> Timber.e(error) })
                .addTo(compositeDisposable)
    }

    fun deleteItem(rosterItem: RosterItem) {
        rosterItemInteractor.deleteRosterItem(rosterItem)
                .subscribe({}, { error -> Timber.e(error)})
                .addTo(compositeDisposable)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
    }

    private fun loadRoster() {
        rosterInteractor.getRoster(rosterId)
                .subscribe({
                    roster = it
                    rosterLiveData.value = it
                }, { error -> Timber.e(error) })
                .addTo(compositeDisposable)
    }

    fun changeTitle(title: String) {
        if(title == roster.name)
            return
        rosterInteractor.updateRoster(roster.copy(name = title))
                .subscribe({}, { error -> Timber.e(error) })
                .addTo(compositeDisposable)
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(private val router: Router, private val rosterInteractor: RosterInteractor, private val rosterItemInteractor: RosterItemInteractor) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (RosterViewModel::class.java.isAssignableFrom(modelClass)) {
                RosterViewModel(router, rosterInteractor, rosterItemInteractor) as T
            } else {
                throw RuntimeException("Cannot create an instance of $modelClass")
            }
        }
    }
}
