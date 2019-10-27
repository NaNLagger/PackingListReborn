package com.nanlagger.rosters.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager
import com.nanlagger.rosters.domain.entities.RosterItem
import com.nanlagger.utils.ListDiffCallback

class RosterItemAdapter(
        private val checkListener: (RosterItem, Boolean) -> Unit,
        private val editListener: (RosterItem, String) -> Unit,
        private val newItemListener: () -> Unit,
        private val deleteListener: (RosterItem) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: List<RosterItemElement> = emptyList()
    private val delegatesManager = AdapterDelegatesManager<List<RosterItemElement>>().apply {
        val editListenerByPosition = { position: Int, s: String ->
            val rosterItemElement = items.getOrNull(position)
            if (rosterItemElement is RosterItemElement.Default) {
                rosterItemElement.rosterItem.name = s
                editListener(rosterItemElement.rosterItem, s)
            }
        }
        val nextListener = { position: Int ->
            val element = items.getOrNull(position + 1)
            if (element is RosterItemElement.NewElement)
                newItemListener()
            element is RosterItemElement.NewElement
        }
        this.addDelegate(DefaultRosterItemDelegate(checkListener, editListenerByPosition, deleteListener, nextListener))
        this.addDelegate(NewElementDelegate(newItemListener))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegatesManager.onCreateViewHolder(parent, viewType)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegatesManager.onBindViewHolder(items, position, holder)
    }

    override fun getItemViewType(position: Int): Int {
        return delegatesManager.getItemViewType(items, position)
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        delegatesManager.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        delegatesManager.onViewDetachedFromWindow(holder)
    }

    class RosterItemDiff(oldList: List<RosterItemElement>, newList: List<RosterItemElement>) : ListDiffCallback<RosterItemElement>(oldList, newList) {

        override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
            val oldElement = oldList[oldPosition]
            val newElement = newList[newPosition]
            return (oldElement is RosterItemElement.Default && newElement is RosterItemElement.Default && oldElement.rosterItem.id == newElement.rosterItem.id)
                    || (oldElement is RosterItemElement.NewElement && newElement is RosterItemElement.NewElement)
        }
    }
}