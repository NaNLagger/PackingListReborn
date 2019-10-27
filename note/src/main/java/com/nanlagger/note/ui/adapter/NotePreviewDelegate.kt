package com.nanlagger.note.ui.adapter

import android.support.v7.widget.helper.ItemTouchHelper
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import com.nanlagger.note.domain.Note

abstract class NotePreviewDelegate(
        protected val clickListener: (Note) -> Unit,
        protected val longClickListener: (Note) -> Unit,
        protected val itemTouchHelper: ItemTouchHelper
) : AdapterDelegate<Note>()