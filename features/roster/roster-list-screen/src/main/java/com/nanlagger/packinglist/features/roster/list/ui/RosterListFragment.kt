package com.nanlagger.packinglist.features.roster.list.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nanlagger.packinglist.core.common.BaseFragment
import com.nanlagger.packinglist.features.roster.list.R
import com.nanlagger.packinglist.features.roster.list.databinding.FragmentRosterListBinding
import com.nanlagger.packinglist.features.roster.list.di.RosterListComponentHolder
import com.nanlagger.packinglist.features.roster.list.ui.adapter.RosterDiff
import com.nanlagger.packinglist.features.roster.list.ui.adapter.RosterAdapter
import com.nanlagger.utils.viewbinding.viewBinding
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class RosterListFragment : BaseFragment() {

    override val layoutId: Int
        get() = R.layout.fragment_roster_list

    @Inject
    lateinit var factory: RosterListViewModel.Factory

    private val viewModel: RosterListViewModel by viewModels { factory }
    private val binding: FragmentRosterListBinding by viewBinding(FragmentRosterListBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RosterListComponentHolder
            .createOrGetComponent(screenName, parentScreenName)
            .inject(this)
    }

    private val rosterAdapter by lazy {
        RosterAdapter(viewModel::openRoster, itemTouchHelper)
    }

    private val dragAndDropCallback = object : ItemTouchHelper.Callback() {
        override fun isLongPressDragEnabled(): Boolean {
            return false
        }

        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
            return makeMovementFlags(dragFlags, swipeFlags)
        }

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            viewModel.changePriority(viewHolder.adapterPosition, target.adapterPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            viewModel.deleteRoster(viewHolder.adapterPosition)
        }

        override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
            super.clearView(recyclerView, viewHolder)
            viewModel.saveOrder()
        }
    }
    private val itemTouchHelper = ItemTouchHelper(dragAndDropCallback)
    val containerSharedView: View?
        get() = rosterAdapter.containerSharedView
    val toolbarSharedView: View?
        get() = rosterAdapter.toolbarSharedView
    val titleSharedView: View?
        get() = rosterAdapter.titleSharedView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerRosters.layoutManager = LinearLayoutManager(context)
        binding.recyclerRosters.adapter = rosterAdapter
        itemTouchHelper.attachToRecyclerView(binding.recyclerRosters)
        binding.buttonNewRoster.setOnClickListener { viewModel.newRoster() }

        viewModel.rosterList.observe(viewLifecycleOwner, Observer { rostersList ->
            if (rostersList == null)
                return@Observer
            val diffResult = DiffUtil.calculateDiff(RosterDiff(rosterAdapter.items, rostersList))
            rosterAdapter.items = rostersList
            diffResult.dispatchUpdatesTo(rosterAdapter)
        })
        viewModel.init()
    }

    override fun onBackPressed() {
        viewModel.back()
    }

    override fun onFinallyFinished() {
        super.onFinallyFinished()
        RosterListComponentHolder.deleteComponent(screenName)
    }

    companion object {
        fun newInstance() = RosterListFragment()
    }
}