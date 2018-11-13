package com.nanlagger.packinglist.ui.rosters

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import com.nanlagger.packinglist.R
import com.nanlagger.packinglist.di.rosterListModule
import com.nanlagger.packinglist.ui.common.BaseFragment
import com.nanlagger.packinglist.ui.rosters.adapter.RosterAdapter
import kotlinx.android.synthetic.main.fragment_roster_list.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance

class RosterListFragment: BaseFragment(), KodeinAware {

    private val _parentKodein by closestKodein { context!! }
    override val kodein: Kodein = Kodein.lazy {
        extend(_parentKodein)
        bind() from instance(this@RosterListFragment)
        import(rosterListModule)
    }
    override val layoutId: Int
        get() = R.layout.fragment_roster_list

    private val viewModel: RosterListViewModel by instance()
    private val rosterAdapter by lazy {
        RosterAdapter(viewModel::openRoster, itemTouchHelper)
    }
    private val dragAndDropCallback = object : ItemTouchHelper.Callback() {
        override fun isLongPressDragEnabled(): Boolean {
            return false
        }

        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG, ItemTouchHelper.DOWN or ItemTouchHelper.UP)
        }

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            viewModel.changePriority(viewHolder.adapterPosition, target.adapterPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        }

        override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
            super.clearView(recyclerView, viewHolder)
            viewModel.saveOrder()
        }
    }
    private val itemTouchHelper = ItemTouchHelper(dragAndDropCallback)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerRosters.layoutManager = LinearLayoutManager(context)
        recyclerRosters.adapter = rosterAdapter
        itemTouchHelper.attachToRecyclerView(recyclerRosters)
        buttonNewRoster.setOnClickListener { viewModel.newRoster() }

        viewModel.rosterList.observe(this, Observer { rostersList ->
            if (rostersList == null)
                return@Observer
            val diffResult = DiffUtil.calculateDiff(RosterDiff(rosterAdapter.items, rostersList))
            rosterAdapter.items = rostersList
            diffResult.dispatchUpdatesTo(rosterAdapter)
        })
        viewModel.init()
    }

    companion object {
        fun newInstance() = RosterListFragment()
    }
}