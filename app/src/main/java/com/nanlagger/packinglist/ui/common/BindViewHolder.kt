package com.nanlagger.packinglist.ui.common

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BindViewHolder<in T>(itemView: View): RecyclerView.ViewHolder(itemView) {

    abstract fun bind(value: T)
}