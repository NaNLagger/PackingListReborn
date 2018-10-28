package com.nanlagger.packinglist.ui.rosters.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.nanlagger.packinglist.R
import com.nanlagger.packinglist.domain.entities.Roster
import com.nanlagger.packinglist.tools.inflate
import kotlinx.android.synthetic.main.item_roster.view.*

class RosterAdapter(
        private val clickListener: (Roster) -> Unit
) : RecyclerView.Adapter<RosterAdapter.RosterViewHolder>() {

    var items: List<Roster> = emptyList()

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
            itemView.setOnClickListener { clickListener(roster) }
        }
    }
}