package com.nanlagger.packinglist.ui.roster

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.nanlagger.packinglist.R
import com.nanlagger.packinglist.databinding.FragmentRosterBinding
import com.nanlagger.packinglist.di.RosterComponentHolder
import com.nanlagger.packinglist.navigation.Screens
import com.nanlagger.packinglist.tools.ArgumentsHolder
import com.nanlagger.packinglist.tools.viewBinding
import com.nanlagger.packinglist.ui.common.BaseFragment
import com.nanlagger.packinglist.ui.roster.adapter.RosterItemAdapter
import java.util.*
import javax.inject.Inject

class RosterFragment : BaseFragment(), ArgumentsHolder<Long> {
    override val layoutId: Int
        get() = R.layout.fragment_roster

    val rosterId
        get() = args

    @Inject
    lateinit var factory: RosterViewModel.Factory

    private val viewModel: RosterViewModel by viewModels { factory }
    private val binding: FragmentRosterBinding by viewBinding(FragmentRosterBinding::bind)

    private val rosterItemAdapter: RosterItemAdapter by lazy { RosterItemAdapter(viewModel::checkItem) }

    override fun onCreate(savedInstanceState: Bundle?) {
        RosterComponentHolder
            .createOrGetComponent(screenName, parentScreenName)
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            toolbarLayout.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
            toolbarLayout.toolbar.setNavigationOnClickListener { viewModel.back() }

            recyclerItems.layoutManager = LinearLayoutManager(requireContext())
            recyclerItems.adapter = rosterItemAdapter

            containerTransition.transitionName = Screens.SHARED_NAME_CONTAINER + rosterId
            toolbarLayout.toolbar.transitionName = Screens.SHARED_NAME_TOOLBAR + rosterId
            textTitle.transitionName = Screens.SHARED_NAME_TITLE + rosterId

            buttonNewItem.setOnClickListener { viewModel.newItem("Item ${Random(System.currentTimeMillis()).nextInt()}") }
        }

        viewModel.init(rosterId)
        viewModel.rosterUI.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            binding.toolbarLayout.toolbar.title = it.name
            val diffResult =
                DiffUtil.calculateDiff(RosterItemAdapter.RosterItemDiff(rosterItemAdapter.items, it.items))
            rosterItemAdapter.items = it.items
            diffResult.dispatchUpdatesTo(rosterItemAdapter)
        })
    }

    companion object {
        fun newInstance(rosterId: Long): RosterFragment {
            return RosterFragment().also { it.args = rosterId }
        }
    }
}
