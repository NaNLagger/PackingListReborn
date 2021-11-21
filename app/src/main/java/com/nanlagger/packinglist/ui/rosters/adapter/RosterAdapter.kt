package com.nanlagger.packinglist.ui.rosters.adapter

import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.nanlagger.packinglist.R
import com.nanlagger.packinglist.databinding.ItemRosterBinding
import com.nanlagger.packinglist.domain.entities.Roster
import com.nanlagger.packinglist.navigation.Screens
import com.nanlagger.packinglist.tools.inflate

class RosterAdapter(
    private val clickListener: (Roster) -> Unit,
    private val itemTouchHelper: ItemTouchHelper
) : RecyclerView.Adapter<RosterAdapter.RosterViewHolder>() {

    var items: List<Roster> = emptyList()
    var containerSharedView: View? = null
    var toolbarSharedView: View? = null
    var titleSharedView: View? = null

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
            //TODO refactor this
            binding.textProgressPercent.text = "${roster.progress}%"
            binding.textTitle.text = roster.name
            binding.textProgressFraction.text = "${roster.checkedCount}/${roster.totalCount}"
            binding.root.transitionName = Screens.SHARED_NAME_CONTAINER + roster.id
            binding.textProgressPercent.transitionName = Screens.SHARED_NAME_TOOLBAR + roster.id
            binding.textTitle.transitionName = Screens.SHARED_NAME_TITLE + roster.id
            binding.root.setOnClickListener {
                containerSharedView = binding.root
                toolbarSharedView = binding.textProgressPercent
                titleSharedView = binding.textTitle
                clickListener(roster)
            }
            binding.imageDrag.setOnTouchListener { _, motionEvent ->
                if (motionEvent.actionMasked == MotionEvent.ACTION_DOWN) {
                    itemTouchHelper.startDrag(this)
                }
                false
            }
        }
    }
}