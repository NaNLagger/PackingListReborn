package com.nanlagger.packinglist.ui.roster

import android.arch.lifecycle.Observer
import android.os.Build
import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.nanlagger.packinglist.R
import com.nanlagger.packinglist.di.rosterModule
import com.nanlagger.packinglist.navigation.Screens
import com.nanlagger.packinglist.ui.common.BaseFragment
import com.nanlagger.packinglist.ui.roster.adapter.RosterItemAdapter
import kotlinx.android.synthetic.main.fragment_roster.*
import kotlinx.android.synthetic.main.toolbar.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import java.util.*

class RosterFragment : BaseFragment(), KodeinAware {
    private val _parentKodein by closestKodein { context!! }
    override val kodein: Kodein = Kodein.lazy {
        extend(_parentKodein)
        bind() from instance(this@RosterFragment)
        import(rosterModule)
    }
    override val layoutId: Int
        get() = R.layout.fragment_roster

    val rosterId
        get() = arguments?.getLong(KEY_ROSTER_ID, 0L) ?: 0L

    companion object {

        private const val KEY_ROSTER_ID = "key.roster.id"

        fun newInstance(rosterId: Long): RosterFragment {
            val bundle = Bundle()
            bundle.putLong(KEY_ROSTER_ID, rosterId)
            return RosterFragment().also { it.arguments = bundle }
        }
    }

    private val viewModel: RosterViewModel by instance()
    private val rosterItemAdapter: RosterItemAdapter by lazy { RosterItemAdapter(viewModel::checkItem) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener { viewModel.back() }

        recyclerItems.layoutManager = LinearLayoutManager(this.context)
        recyclerItems.adapter = rosterItemAdapter

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            containerTransition.transitionName = Screens.SHARED_NAME_CONTAINER + rosterId
            toolbar.transitionName = Screens.SHARED_NAME_TOOLBAR + rosterId
            textTitle.transitionName = Screens.SHARED_NAME_TITLE + rosterId
        }

        buttonNewItem.setOnClickListener { viewModel.newItem("Item ${Random(System.currentTimeMillis()).nextInt()}") }

        viewModel.init(rosterId)
        viewModel.rosterUI.observe(this, Observer {
            if (it == null) return@Observer
            toolbar.title = it.name
            val diffResult = DiffUtil.calculateDiff(RosterItemAdapter.RosterItemDiff(rosterItemAdapter.items, it.items))
            rosterItemAdapter.items = it.items
            diffResult.dispatchUpdatesTo(rosterItemAdapter)
        })
    }
}
