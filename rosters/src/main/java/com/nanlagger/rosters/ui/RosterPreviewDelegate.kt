package com.nanlagger.rosters.ui

import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.nanlagger.note.domain.Note
import com.nanlagger.note.ui.adapter.NotePreviewDelegate
import com.nanlagger.note.ui.adapter.NoteViewHolder
import com.nanlagger.note.ui.ProgressView
import com.nanlagger.rosters.R
import com.nanlagger.rosters.domain.entities.Roster
import com.nanlagger.utils.inflate

class RosterPreviewDelegate(
        clickListener: (Note) -> Unit,
        longClickListener: (Note) -> Unit,
        itemTouchHelper: ItemTouchHelper
) : NotePreviewDelegate(clickListener, longClickListener, itemTouchHelper) {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return RosterPreviewViewHolder(parent.inflate(R.layout.item_roster))
    }

    override fun isForViewType(item: Note, position: Int): Boolean {
        return item is Roster
    }

    override fun onBindViewHolder(item: Note, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        if(item is Roster && holder is RosterPreviewViewHolder) {
            holder.bind(item)
        }
    }

    inner class RosterPreviewViewHolder(itemView: View) : NoteViewHolder<Roster>(clickListener, longClickListener, itemTouchHelper, itemView) {

        override val dragElement: View = itemView.findViewById(R.id.imageDrag)
        override val swipeableView: View = itemView.findViewById(R.id.containerSwipeable)
        override val animatedIcon: AppCompatImageView = itemView.findViewById(R.id.imageDelete)

        private val textProgressPercent: ProgressView = itemView.findViewById(R.id.textProgressPercent)
        private val textTitle: TextView = itemView.findViewById(R.id.textTitle)
        private val textProgressFraction: TextView = itemView.findViewById(R.id.textProgressFraction)

        override fun bind(value: Roster) {
            super.bind(value)
            textProgressPercent.text = "${value.progress}%"
            textTitle.text = value.name
            textProgressFraction.text = itemView.context.resources.getString(R.string.roster_item_progress, value.checkedCount, value.totalCount)
            textProgressPercent.progress = value.progress * 0.01f
        }
    }
}