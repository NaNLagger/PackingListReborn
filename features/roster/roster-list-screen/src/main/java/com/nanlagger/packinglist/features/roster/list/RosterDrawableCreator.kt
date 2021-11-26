package com.nanlagger.packinglist.features.roster.list

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import androidx.annotation.ColorInt
import com.nanlagger.packinglist.core.common.ResourcesProvider
import javax.inject.Inject

class RosterDrawableCreator @Inject constructor(
    private val resourcesProvider: ResourcesProvider
) {

    private val cornersRadius = resourcesProvider.getDimen(R.dimen.roster_progress_corner_radius).toFloat()

    private val cacheDrawable: MutableMap<Int, Drawable> = mutableMapOf()

    fun createRoundBackgroundByProgress(progress: Int): Drawable {
        val colorByProgress = colorByProgress(progress)
        val drawable = cacheDrawable[colorByProgress]
        return if (drawable != null) {
            drawable
        } else {
            val corners = FloatArray(CORNERS_ARRAY_SIZE) { cornersRadius }
            val roundRectShape = RoundRectShape(corners, null, null)
            ShapeDrawable(roundRectShape).apply {
                paint.color = colorByProgress
            }.also { cacheDrawable[colorByProgress] = it }
        }
    }

    @ColorInt
    private fun colorByProgress(progress: Int): Int {
        val colors = resourcesProvider.getArray(R.array.progress_color_values_new)
        val index = progress / (colors.size - 1)
        val colorHex = colors.getOrNull(index) ?: return Color.BLUE
        return Color.parseColor(colorHex)
    }

    companion object {
        private const val CORNERS_ARRAY_SIZE = 8
    }
}