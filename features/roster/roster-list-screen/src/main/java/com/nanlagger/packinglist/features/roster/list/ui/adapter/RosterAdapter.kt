package com.nanlagger.packinglist.features.roster.list.ui.adapter

import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.nanlagger.packinglist.features.roster.common.RosterSharedNames
import com.nanlagger.packinglist.features.roster.domain.entities.Roster
import com.nanlagger.packinglist.features.roster.list.R
import com.nanlagger.packinglist.features.roster.list.databinding.ItemRosterBinding
import com.nanlagger.utils.extensions.inflate

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
            binding.root.transitionName = RosterSharedNames.SHARED_NAME_CONTAINER + roster.id
            binding.textProgressPercent.transitionName = RosterSharedNames.SHARED_NAME_TOOLBAR + roster.id
            binding.textTitle.transitionName = RosterSharedNames.SHARED_NAME_TITLE + roster.id
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