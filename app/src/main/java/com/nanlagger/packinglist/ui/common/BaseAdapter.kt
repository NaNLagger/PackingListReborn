package com.nanlagger.packinglist.ui.common

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

abstract class BaseAdapter<T: Any, VH: BindViewHolder<T>>: RecyclerView.Adapter<VH>() {

    var items: List<T> = listOf()

    abstract override fun onCreateViewHolder(parent: ViewGroup, position: Int): VH

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(viewHolder: VH, position: Int) {
        viewHolder.bind(items[position])
    }

}