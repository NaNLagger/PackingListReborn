package com.nanlagger.packinglist.ui.rosters

import com.nanlagger.packinglist.R
import com.nanlagger.packinglist.ui.common.BaseFragment
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
//        import(createTagModule)
    }
    override val layoutId: Int
        get() = R.layout.fragment_roster_list

    companion object {
        fun newInstance() = RostersListFragment()
    }
}