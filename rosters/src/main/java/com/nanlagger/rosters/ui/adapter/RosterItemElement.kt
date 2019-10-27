package com.nanlagger.rosters.ui.adapter

import com.nanlagger.rosters.domain.entities.RosterItem

sealed class RosterItemElement {

    data class Default(var rosterItem: RosterItem): RosterItemElement()

    object NewElement : RosterItemElement()

    object RosterItemElementComparator : Comparator<RosterItemElement> {
        override fun compare(p0: RosterItemElement?, p1: RosterItemElement?): Int {
            val firstPriority1 = firstPriority(p0)
            val firstPriority2 = firstPriority(p1)
            return if (firstPriority1 - firstPriority2 == 0) {
                secondPriority(p0) - secondPriority(p1)
            } else {
                firstPriority1 - firstPriority2
            }
        }

        private fun firstPriority(element: RosterItemElement?): Int {
            return when (element) {
                is Default -> if (element.rosterItem.checked) 2 else 0
                is NewElement -> 1
                else -> -1
            }
        }

        private fun secondPriority(element: RosterItemElement?): Int {
            return when (element) {
                is Default -> element.rosterItem.id.toInt()
                is NewElement -> 0
                else -> -1
            }
        }

    }
}