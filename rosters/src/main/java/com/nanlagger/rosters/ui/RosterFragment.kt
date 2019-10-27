package com.nanlagger.rosters.ui

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.jakewharton.rxbinding3.widget.textChanges
import com.nanlagger.rosters.ui.adapter.RosterItemAdapter
import com.nanlagger.rosters.ui.adapter.RosterItemElement
import com.nanlagger.rosters.R
import com.nanlagger.rosters.di.RosterModule
import com.nanlagger.utils.BaseFragment
import com.nanlagger.utils.addTo
import com.nanlagger.utils.argument
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_roster.*
import toothpick.Scope
import toothpick.Toothpick
import java.util.concurrent.TimeUnit

class RosterFragment : BaseFragment() {
    override val layoutId: Int
        get() = R.layout.fragment_roster

    private val rosterId: Long by argument(KEY_ROSTER_ID, 0L)
    private val parentScopeName: String by argument(KEY_PARENT_SCOPE_NAME, "")

    companion object {

        private const val KEY_ROSTER_ID = "key.roster.id"
        private const val KEY_PARENT_SCOPE_NAME = "key.parent.scope.name"

        fun newInstance(rosterId: Long, parentScopeName: String): RosterFragment {
            val bundle = Bundle()
            bundle.putLong(KEY_ROSTER_ID, rosterId)
            bundle.putString(KEY_PARENT_SCOPE_NAME, parentScopeName)
            return RosterFragment().also { it.arguments = bundle }
        }
    }

    private val viewModel: RosterViewModel by lazy { scope.getInstance(RosterViewModel::class.java) }
    private val rosterItemAdapter: RosterItemAdapter by lazy { RosterItemAdapter(viewModel::checkItem, viewModel::editItem, viewModel::newItem, viewModel::deleteItem) }
    private val compositeDisposable = CompositeDisposable()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.init(rosterId)
        viewModel.rosterUI.observe(this, Observer {
            if (it == null) return@Observer
            toolbar.title = it.name
            val elementsForAdapter = (it.items.asSequence()
                    .map { rosterItem -> RosterItemElement.Default(rosterItem) } + RosterItemElement.NewElement)
                    .sortedWith(RosterItemElement.RosterItemElementComparator)
                    .toList()
            val diffResult = DiffUtil.calculateDiff(RosterItemAdapter.RosterItemDiff(rosterItemAdapter.items, elementsForAdapter))
            rosterItemAdapter.items = elementsForAdapter
            diffResult.dispatchUpdatesTo(rosterItemAdapter)
            if (!editTitle.hasFocus())
                editTitle.setText(it.name)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener { viewModel.back() }

        recyclerItems.layoutManager = LinearLayoutManager(this.context)
        recyclerItems.adapter = rosterItemAdapter

        editTitle.textChanges()
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribe { viewModel.changeTitle(it.toString()) }
                .addTo(compositeDisposable)

        editTitle.setHorizontallyScrolling(false)
        editTitle.maxLines = 99
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.dispose()
    }

    override fun onOpenScope(): Scope {
        return Toothpick.openScopes(parentScopeName, scopeName)
                .apply { installModules(RosterModule()) }
    }

    override fun onCloseScope() {
        Toothpick.closeScope(scopeName)
    }
}
