package com.nanlagger.packinglist.ui.common

import android.support.v7.widget.RecyclerView
import android.view.View

abstract class BindViewHolder<in T>(itemView: View): RecyclerView.ViewHolder(itemView) {

    abstract fun bind(value: T)
}