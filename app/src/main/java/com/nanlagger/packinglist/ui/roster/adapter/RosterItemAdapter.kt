package com.nanlagger.packinglist.ui.roster.adapter

import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import com.nanlagger.packinglist.R
import com.nanlagger.packinglist.domain.entities.RosterItem
import com.nanlagger.packinglist.tools.ListDiffCallback
import com.nanlagger.packinglist.tools.inflate
import com.nanlagger.packinglist.ui.common.BaseAdapter
import com.nanlagger.packinglist.ui.common.BindViewHolder
import kotlinx.android.synthetic.main.item_roster_item.view.*

class RosterItemAdapter(
        private val checkListener: (RosterItem, Boolean) -> Unit
): BaseAdapter<RosterItem, RosterItemAdapter.RosterItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RosterItemViewHolder {
        return RosterItemViewHolder(parent.inflate(R.layout.item_roster_item))
    }

    inner class RosterItemViewHolder(itemView: View): BindViewHolder<RosterItem>(itemView) {
        override fun bind(value: RosterItem) {
            itemView.textName.text = value.name
            itemView.checkItem.isChecked = value.checked
            itemView.checkItem.text = value.name
            itemView.checkItem.setOnClickListener { checkListener(value, !value.checked) }
            if (value.checked) {
                itemView.textName.paintFlags = itemView.textName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                itemView.textName.paintFlags = itemView.textName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
    }

    class RosterItemDiff(oldList: List<RosterItem>, newList: List<RosterItem>) : ListDiffCallback<RosterItem>(oldList, newList) {

        override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
            return oldList[oldPosition].id == newList[newPosition].id
        }
    }
}