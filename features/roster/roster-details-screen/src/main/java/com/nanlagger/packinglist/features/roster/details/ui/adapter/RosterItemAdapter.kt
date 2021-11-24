package com.nanlagger.packinglist.features.roster.details.ui.adapter

import android.view.View
import android.view.ViewGroup
import com.nanlagger.packinglist.features.roster.details.R
import com.nanlagger.packinglist.features.roster.domain.entities.RosterItem
import com.nanlagger.utils.adapter.BaseAdapter
import com.nanlagger.utils.adapter.BindViewHolder
import com.nanlagger.utils.adapter.ListDiffCallback
import com.nanlagger.utils.extensions.inflate

class RosterItemAdapter(
    private val checkListener: (RosterItem, Boolean) -> Unit
) : BaseAdapter<RosterItem, RosterItemAdapter.RosterItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RosterItemViewHolder {
        return RosterItemViewHolder(parent.inflate(R.layout.item_roster_item))
    }

    inner class RosterItemViewHolder(itemView: View) : BindViewHolder<RosterItem>(itemView) {
        override fun bind(value: RosterItem) {
//            itemView.textName.text = value.name
//            itemView.checkItem.isChecked = value.checked
//            itemView.checkItem.text = value.name
//            itemView.checkItem.setOnClickListener { checkListener(value, !value.checked) }
//            if (value.checked) {
//                itemView.textName.paintFlags = itemView.textName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
//            } else {
//                itemView.textName.paintFlags = itemView.textName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
//            }
        }
    }

    class RosterItemDiff(oldList: List<RosterItem>, newList: List<RosterItem>) :
        ListDiffCallback<RosterItem>(oldList, newList) {

        override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
            return oldList[oldPosition].id == newList[newPosition].id
        }
    }
}