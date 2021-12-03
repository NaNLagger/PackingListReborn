package com.nanlagger.packinglist.features.roster.details.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.nanlagger.packinglist.core.common.BaseFragment
import com.nanlagger.packinglist.features.editName.ui.EditNameDialog
import com.nanlagger.packinglist.features.roster.common.RosterTransitionAnimationHelper
import com.nanlagger.packinglist.features.roster.details.R
import com.nanlagger.packinglist.features.roster.details.databinding.FragmentRosterBinding
import com.nanlagger.packinglist.features.roster.details.di.RosterComponentHolder
import com.nanlagger.packinglist.features.roster.details.ui.adapter.RosterItemAdapter
import com.nanlagger.packinglist.features.roster.domain.entities.Roster
import com.nanlagger.utils.ArgumentsHolder
import com.nanlagger.utils.viewbinding.viewBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class RosterFragment : BaseFragment(), ArgumentsHolder<Long> {
    override val layoutId: Int
        get() = R.layout.fragment_roster

    private val rosterId
        get() = args

    @Inject
    lateinit var factory: RosterViewModel.Factory
    @Inject
    lateinit var rosterTransitionAnimationHelper: RosterTransitionAnimationHelper

    private val viewModel: RosterViewModel by viewModels { factory }
    private val binding: FragmentRosterBinding by viewBinding(FragmentRosterBinding::bind)

    private val rosterItemAdapter: RosterItemAdapter by lazy { RosterItemAdapter(viewModel::checkItem) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RosterComponentHolder
            .createOrGetComponent(screenName, parentScreenName)
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            toolbarLayout.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
            toolbarLayout.toolbar.setNavigationOnClickListener { viewModel.back() }

            recyclerItems.layoutManager = LinearLayoutManager(requireContext())
            recyclerItems.adapter = rosterItemAdapter

            rosterTransitionAnimationHelper.setSharedName(rosterId, containerTransition, toolbarLayout.toolbar, textTitle)

            buttonNewItem.setOnClickListener { openEditNameDialog() }
        }

        viewModel.init(rosterId)
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { bindUi(it) }
        }
    }

    private fun bindUi(it: Roster) {
        binding.toolbarLayout.toolbar.title = it.name
        val diffResult =
            DiffUtil.calculateDiff(RosterItemAdapter.RosterItemDiff(rosterItemAdapter.items, it.items))
        rosterItemAdapter.items = it.items
        diffResult.dispatchUpdatesTo(rosterItemAdapter)
    }

    override fun onBackPressed() {
        viewModel.back()
    }

    override fun onFinallyFinished() {
        super.onFinallyFinished()
        RosterComponentHolder.deleteComponent(screenName)
    }

    private fun openEditNameDialog() {
        EditNameDialog.newInstance()
            .show(childFragmentManager, EDIT_NAME_DIALOG_TAG)
    }

    companion object {
        private const val EDIT_NAME_DIALOG_TAG = "EDIT_NAME_DIALOG_TAG"

        fun newInstance(rosterId: Long): RosterFragment {
            return RosterFragment().also { it.args = rosterId }
        }
    }
}
