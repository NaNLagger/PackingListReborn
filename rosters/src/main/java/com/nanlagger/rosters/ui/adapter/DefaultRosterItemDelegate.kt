package com.nanlagger.rosters.ui.adapter

import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import com.jakewharton.rxbinding3.view.focusChanges
import com.jakewharton.rxbinding3.widget.editorActions
import com.jakewharton.rxbinding3.widget.textChanges
import com.nanlagger.rosters.R
import com.nanlagger.rosters.domain.entities.RosterItem
import com.nanlagger.utils.*
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class DefaultRosterItemDelegate(
        private val checkListener: (RosterItem, Boolean) -> Unit,
        private val editListener: (Int, String) -> Unit,
        private val deleteListener: (RosterItem) -> Unit,
        private val nextListener: (Int) -> Boolean
) : AdapterDelegate<List<RosterItemElement>>() {

    override fun isForViewType(items: List<RosterItemElement>, position: Int) = items[position] is RosterItemElement.Default

    override fun onBindViewHolder(items: List<RosterItemElement>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        val item = items[position]
        if (holder is RosterItemViewHolder && item is RosterItemElement.Default) {
            holder.bind(item.rosterItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return RosterItemViewHolder(parent.inflate(R.layout.item_roster_item))
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        if (holder is RosterItemViewHolder) holder.onAttached()
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder?) {
        if (holder is RosterItemViewHolder) holder.onDetached()
    }

    inner class RosterItemViewHolder(itemView: View) : BindViewHolder<RosterItem>(itemView) {
        private var compositeDisposable = CompositeDisposable()

        private val textName: EditText = itemView.findViewById(R.id.textName)
        private val checkItem: CheckBox = itemView.findViewById(R.id.checkItem)
        private val imageDelete: ImageView = itemView.findViewById(R.id.imageDelete)

        fun onAttached() {
            compositeDisposable = CompositeDisposable()
            textName.setHorizontallyScrolling(false)
            textName.maxLines = 99
            textName.textChanges()
                    .debounce(500, TimeUnit.MILLISECONDS)
                    .subscribe { s -> editListener(adapterPosition, s.toString()) }
                    .addTo(compositeDisposable)
            textName.focusChanges()
                    .subscribe { imageDelete.visibleOrInvisible(it) }
                    .addTo(compositeDisposable)
            textName.editorActions { it == EditorInfo.IME_ACTION_NEXT && nextListener(adapterPosition) }
                    .subscribe()
                    .addTo(compositeDisposable)
        }

        fun onDetached() {
            compositeDisposable.dispose()
        }

        override fun bind(value: RosterItem) {
            textName.setText(value.name)
            checkItem.isChecked = value.checked
            checkItem.setOnClickListener { checkListener(value, !value.checked) }
            imageDelete.setOnClickListener { deleteListener(value) }
            if (value.checked) {
                textName.paintFlags = textName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                textName.paintFlags = textName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
            if (value.name.isBlank()) {
                textName.post { textName.showKeyboard() }
            }
        }
    }
}