package com.nanlagger.packinglist.ui.rosters.adapter

import android.os.Build
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.nanlagger.packinglist.R
import com.nanlagger.packinglist.domain.entities.Roster
import com.nanlagger.packinglist.navigation.Screens
import com.nanlagger.packinglist.tools.inflate
import kotlinx.android.synthetic.main.item_roster.view.*

class RosterAdapter(
        private val clickListener: (Roster) -> Unit,
        private val itemTouchHelper: ItemTouchHelper
) : RecyclerView.Adapter<RosterAdapter.RosterViewHolder>() {

    var items: List<Roster> = emptyList()
    var containerSharedView: View? = null
    var toolbarSharedView: View? = null
    var titleSharedView: View? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RosterViewHolder {
        return RosterViewHolder(parent.inflate(R.layout.item_roster))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RosterViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class RosterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(roster: Roster) {
            itemView.textProgressPercent.text = "${roster.progress}%"
            itemView.textTitle.text = roster.name
            itemView.textProgressFraction.text = "${roster.checkedCount}/${roster.totalCount}"
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                itemView.transitionName = Screens.SHARED_NAME_CONTAINER + roster.id
                itemView.textProgressPercent.transitionName = Screens.SHARED_NAME_TOOLBAR + roster.id
                itemView.textTitle.transitionName = Screens.SHARED_NAME_TITLE + roster.id
            }
            itemView.setOnClickListener {
                containerSharedView = itemView
                toolbarSharedView = itemView.textProgressPercent
                titleSharedView = itemView.textTitle
                clickListener(roster)
            }
            itemView.imageDrag.setOnTouchListener { _, motionEvent ->
                if(motionEvent.actionMasked == MotionEvent.ACTION_DOWN) {
                        itemTouchHelper.startDrag(this)
                }
                false
            }
        }
    }
}