package com.nanlagger.note.ui

import com.nanlagger.note.domain.Note
import com.nanlagger.utils.ListDiffCallback

class NoteDiff(oldList: List<Note>, newList: List<Note>) : ListDiffCallback<Note>(oldList, newList) {

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.id == newItem.id
                && oldItem.priority == newItem.priority
    }

    override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return oldList[oldPosition].id == newList[newPosition].id
    }
}