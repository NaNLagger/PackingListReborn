package com.nanlagger.utils.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T: Any, VH: BindViewHolder<T>>: RecyclerView.Adapter<VH>() {

    var items: List<T> = listOf()

    abstract override fun onCreateViewHolder(parent: ViewGroup, position: Int): VH

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(viewHolder: VH, position: Int) {
        viewHolder.bind(items[position])
    }

}