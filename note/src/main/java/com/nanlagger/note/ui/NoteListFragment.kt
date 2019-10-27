package com.nanlagger.note.ui

import android.arch.lifecycle.Observer
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import com.nanlagger.note.R
import com.nanlagger.note.di.NoteModule
import com.nanlagger.note.ui.adapter.NoteViewHolder
import com.nanlagger.note.ui.adapter.NoteAdapter
import com.nanlagger.utils.*
import kotlinx.android.synthetic.main.fragment_roster_list.*
import toothpick.Scope
import toothpick.Toothpick


class NoteListFragment : BaseFragment(), OnBackListener {

    companion object {

        private const val KEY_PARENT_SCOPE_NAME = "key.parent.scope.name"

        fun newInstance(parentScopeName: String): NoteListFragment {
            val bundle = Bundle()
            bundle.putString(KEY_PARENT_SCOPE_NAME, parentScopeName)
            return NoteListFragment().also { it.arguments = bundle }
        }
    }

    private val parentScopeName: String by argument(KEY_PARENT_SCOPE_NAME, "")

    override val layoutId: Int
        get() = R.layout.fragment_roster_list

    private val viewModel: NoteListViewModel by lazy { scope.getInstance(NoteListViewModel::class.java) }
    private val noteAdapter by lazy { scope.getInstance(NoteAdapter::class.java) }
    private val dragAndDropCallback = object : ItemTouchHelper.Callback() {
        override fun isLongPressDragEnabled(): Boolean {
            return false
        }

        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
            return makeMovementFlags(dragFlags, swipeFlags)
        }

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            viewModel.changePriority(viewHolder.adapterPosition, target.adapterPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            viewModel.deleteNote(viewHolder.adapterPosition)
        }

        override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE && viewHolder is NoteViewHolder<*>) {
                val layoutParams = viewHolder.animatedIcon.layoutParams as FrameLayout.LayoutParams
                layoutParams.gravity = (if (dX > 0) Gravity.START else Gravity.END) or Gravity.CENTER_VERTICAL
                viewHolder.animatedIcon.layoutParams = layoutParams
                getDefaultUIUtil().onDraw(c, recyclerView, viewHolder.swipeableView, dX, dY, actionState, isCurrentlyActive)
            } else {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }

        override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
            super.clearView(recyclerView, viewHolder)
            if (viewHolder is NoteViewHolder<*>) getDefaultUIUtil().clearView(viewHolder.swipeableView)
            viewModel.saveOrder()
        }
    }
    private val itemTouchHelper = ItemTouchHelper(dragAndDropCallback)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.init()
        viewModel.noteList.observe(this, Observer { rostersList ->
            if (rostersList == null)
                return@Observer
            val diffResult = DiffUtil.calculateDiff(NoteDiff(noteAdapter.items, rostersList))
            noteAdapter.items = rostersList
            diffResult.dispatchUpdatesTo(noteAdapter)
        })
        viewModel.editMode.observe(this, Observer { editMode ->
            if (editMode == null) return@Observer
            appbar.visibleOrGone(!editMode)
            appbarEdit.visibleOrGone(editMode)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerRosters.layoutManager = LinearLayoutManager(context)
        recyclerRosters.adapter = noteAdapter
        itemTouchHelper.attachToRecyclerView(recyclerRosters)
        buttonNewRoster.setOnClickListener { viewModel.newNote("") }

        val navigationIcon = toolbarEdit.navigationIcon
        navigationIcon?.let {
            it.mutate()
            it.setColorFilter(resources.color(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP)
        }
        toolbarEdit.setNavigationOnClickListener { viewModel.back() }
        toolbarEdit.inflateMenu(R.menu.menu_edit_roster_list)
        for (i in 0 until toolbarEdit.menu.size()) {
            val icon = toolbarEdit.menu.getItem(i).icon
            icon?.let {
                it.mutate()
                it.setColorFilter(resources.color(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP)
            }
        }
    }

    override fun onBack() {
        viewModel.back()
    }

    override fun onOpenScope(): Scope {
        return Toothpick.openScopes(parentScopeName, scopeName)
                .apply { installModules(NoteModule(this@NoteListFragment, scopeName)) }
    }

    override fun onCloseScope() {
        Toothpick.closeScope(scopeName)
    }
}