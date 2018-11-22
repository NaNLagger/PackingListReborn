package com.nanlagger.packinglist.ui.roster

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.nanlagger.packinglist.domain.entities.Roster
import com.nanlagger.packinglist.domain.entities.RosterItem
import com.nanlagger.packinglist.domain.interactors.RosterInteractor
import com.nanlagger.packinglist.domain.interactors.RosterItemInteractor
import com.nanlagger.packinglist.tools.addTo
import io.reactivex.disposables.CompositeDisposable
import ru.terrakok.cicerone.Router
import timber.log.Timber

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
        rosterItemInteractor.update(rosterItem.copy(checked = isChecked))
                .subscribe({}, { error -> Timber.e(error) })
                .addTo(compositeDisposable)
    }

    fun newItem(name: String) {
        rosterItemInteractor.addRosterItem(RosterItem(0L, name, rosterId, false))
                .subscribe({}, { error -> Timber.e(error) })
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

    @Suppress("UNCHECKED_CAST")
    class Factory(private val router: Router, private val rosterInteractor: RosterInteractor, private val rosterItemInteractor: RosterItemInteractor) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (RosterViewModel::class.java.isAssignableFrom(modelClass)) {
                RosterViewModel(router, rosterInteractor, rosterItemInteractor) as T
            } else {
                throw RuntimeException("Cannot create an instance of $modelClass")
            }
        }
    }
}
