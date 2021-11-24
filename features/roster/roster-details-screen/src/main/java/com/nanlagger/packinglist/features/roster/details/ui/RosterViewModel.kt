package com.nanlagger.packinglist.features.roster.details.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.Router
import com.nanlagger.packinglist.features.roster.domain.entities.Roster
import com.nanlagger.packinglist.features.roster.domain.entities.RosterItem
import com.nanlagger.packinglist.features.roster.domain.interactors.RosterInteractor
import com.nanlagger.packinglist.features.roster.domain.interactors.RosterItemInteractor
import com.nanlagger.utils.extensions.addTo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
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
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, { error -> Timber.e(error) })
            .addTo(compositeDisposable)
    }

    fun newItem(name: String) {
        rosterItemInteractor.addRosterItem(RosterItem(0L, name, rosterId, false))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, { error -> Timber.e(error) })
            .addTo(compositeDisposable)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
    }

    private fun loadRoster() {
        rosterInteractor.getRoster(rosterId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                roster = it
                rosterLiveData.value = it
            }, { error -> Timber.e(error) })
            .addTo(compositeDisposable)
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val router: Router,
        private val rosterInteractor: RosterInteractor,
        private val rosterItemInteractor: RosterItemInteractor
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (RosterViewModel::class.java.isAssignableFrom(modelClass)) {
                RosterViewModel(router, rosterInteractor, rosterItemInteractor) as T
            } else {
                throw RuntimeException("Cannot create an instance of $modelClass")
            }
        }
    }
}
