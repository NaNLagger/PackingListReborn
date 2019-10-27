package com.nanlagger.note.ui.adapter

import android.support.annotation.CallSuper
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.MotionEvent
import android.view.View
import com.nanlagger.note.domain.Note
import com.nanlagger.utils.BindViewHolder

abstract class NoteViewHolder<in T : Note>(
        private val clickListener: (Note) -> Unit,
        private val longClickListener: (Note) -> Unit,
        private val itemTouchHelper: ItemTouchHelper,
        itemView: View
) : BindViewHolder<T>(itemView) {

    abstract val dragElement: View
    abstract val swipeableView: View
    abstract val animatedIcon: AppCompatImageView

    @CallSuper
    override fun bind(value: T) {
        itemView.setOnClickListener {
            clickListener(value)
        }
        itemView.setOnLongClickListener {
            longClickListener(value)
            true
        }
        dragElement.setOnTouchListener { _, motionEvent ->
            if(motionEvent.actionMasked == MotionEvent.ACTION_DOWN) {
                itemTouchHelper.startDrag(this)
            }
            false
        }
    }
}