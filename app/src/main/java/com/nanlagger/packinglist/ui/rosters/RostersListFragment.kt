package com.nanlagger.packinglist.ui.rosters

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.nanlagger.packinglist.R
import com.nanlagger.packinglist.di.rostersListModule
import com.nanlagger.packinglist.ui.common.BaseFragment
import com.nanlagger.packinglist.ui.rosters.adapter.RosterAdapter
import kotlinx.android.synthetic.main.fragment_roster_list.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance

class RostersListFragment: BaseFragment(), KodeinAware {

    private val _parentKodein by closestKodein { context!! }
    override val kodein: Kodein = Kodein.lazy {
        extend(_parentKodein)
        bind() from instance(this@RostersListFragment)
        import(rostersListModule)
    }
    override val layoutId: Int
        get() = R.layout.fragment_roster_list

    private val viewModel: RostersListViewModel by instance()
    private val rosterAdapter by instance<RosterAdapter>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerRosters.layoutManager = LinearLayoutManager(context)
        recyclerRosters.adapter = rosterAdapter

        viewModel.rostersList.observe(this, Observer { rostersList ->
            if (rostersList == null)
                return@Observer
            rosterAdapter.items = rostersList
            rosterAdapter.notifyDataSetChanged()
        })
    }

    companion object {
        fun newInstance() = RostersListFragment()
    }
}