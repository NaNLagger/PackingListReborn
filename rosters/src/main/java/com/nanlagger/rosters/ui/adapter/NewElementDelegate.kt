package com.nanlagger.rosters.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import com.nanlagger.rosters.R
import com.nanlagger.utils.BindViewHolder
import com.nanlagger.utils.inflate

class NewElementDelegate(
        private val clickListener: () -> Unit
) : AdapterDelegate<List<RosterItemElement>>() {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return NewElementViewHolder(parent.inflate(R.layout.item_new_element))
    }

    override fun isForViewType(items: List<RosterItemElement>, position: Int): Boolean = items[position] is RosterItemElement.NewElement

    override fun onBindViewHolder(items: List<RosterItemElement>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        val item = items[position]
        if (holder is NewElementViewHolder && item is RosterItemElement.NewElement) {
            holder.bind(item)
        }
    }

    inner class NewElementViewHolder(itemView: View) : BindViewHolder<RosterItemElement.NewElement>(itemView) {

        override fun bind(value: RosterItemElement.NewElement) {
            itemView.setOnClickListener {
                clickListener()
            }
        }
    }
}