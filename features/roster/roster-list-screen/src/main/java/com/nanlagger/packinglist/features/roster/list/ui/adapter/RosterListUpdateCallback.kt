package com.nanlagger.packinglist.features.roster.list.ui.adapter

import androidx.recyclerview.widget.ListUpdateCallback

class RosterListUpdateCallback(
    private val adapter: RosterAdapter,
    private val firstItemListener: (Int) -> Unit
) : ListUpdateCallback {

    private var isFirstItem = true

    override fun onInserted(position: Int, count: Int) {
        adapter.notifyItemRangeInserted(position, count)
        if (isFirstItem) {
            firstItemListener(position)
            isFirstItem = false
        }
    }

    override fun onRemoved(position: Int, count: Int) {
        adapter.notifyItemRangeRemoved(position, count)
    }

    override fun onMoved(fromPosition: Int, toPosition: Int) {
        adapter.notifyItemMoved(fromPosition, toPosition)
    }

    override fun onChanged(position: Int, count: Int, payload: Any?) {
        adapter.notifyItemRangeChanged(position, count, payload)
        if (isFirstItem) {
            firstItemListener(position)
            isFirstItem = false
        }
    }
}