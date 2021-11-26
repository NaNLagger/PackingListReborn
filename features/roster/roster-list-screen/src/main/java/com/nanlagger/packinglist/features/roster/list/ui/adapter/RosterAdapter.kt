package com.nanlagger.packinglist.features.roster.list.ui.adapter

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.nanlagger.packinglist.features.roster.common.RosterTransitionAnimationHelper
import com.nanlagger.packinglist.features.roster.domain.entities.Roster
import com.nanlagger.packinglist.features.roster.list.R
import com.nanlagger.packinglist.features.roster.list.RosterDrawableCreator
import com.nanlagger.packinglist.features.roster.list.databinding.ItemRosterBinding
import com.nanlagger.utils.extensions.inflate

class RosterAdapter(
    private val clickListener: (Roster) -> Unit,
    private val itemTouchHelper: ItemTouchHelper,
    private val rosterTransitionAnimationHelper: RosterTransitionAnimationHelper,
    private val rosterDrawableCreator: RosterDrawableCreator
) : RecyclerView.Adapter<RosterAdapter.RosterViewHolder>() {

    var items: List<Roster> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RosterViewHolder {
        return RosterViewHolder(parent.inflate(R.layout.item_roster))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RosterViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class RosterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemRosterBinding.bind(itemView)

        fun bind(roster: Roster) {
            with(binding) {
                textProgressPercent.text = root.resources.getString(R.string.roster_progress_percent, roster.progress)
                textTitle.text = roster.name
                textProgressFraction.text =
                    root.resources.getString(R.string.roster_progress_fraction, roster.checkedCount, roster.totalCount)
                rosterTransitionAnimationHelper.setSharedName(roster.id, root, textProgressPercent, textTitle)
                root.setOnClickListener {
                    rosterTransitionAnimationHelper.setupTransitionViews(root, textProgressPercent, textTitle)
                    clickListener(roster)
                }
                textProgressPercent.background = rosterDrawableCreator.createRoundBackgroundByProgress(roster.progress)
            }
            setupDragAndDrop()
        }

        @SuppressLint("ClickableViewAccessibility")
        private fun setupDragAndDrop() {
            binding.imageDrag.setOnTouchListener { _, motionEvent ->
                if (motionEvent.actionMasked == MotionEvent.ACTION_DOWN) {
                    itemTouchHelper.startDrag(this@RosterViewHolder)
                }
                false
            }
        }
    }
}