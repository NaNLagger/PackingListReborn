package com.nanlagger.note.ui

import android.content.Context
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.widget.TextView
import com.nanlagger.note.R

class ProgressView(context: Context?, attrs: AttributeSet?) : TextView(context, attrs) {

    private var _progress = 0f
    var progress: Float
        get() = _progress
        set(value) {
            _progress = value
            if (_progress < 0f) _progress = 0f
            if (_progress > 1f) _progress = 1f
            var index = (_progress * colors.size).toInt()
            if (index > colors.size - 1) index = colors.size - 1
            outlineCirclePaint.color = colors[index]
            invalidate()
            requestLayout()
        }

    private var _outlineColor = Color.BLUE
    var outlineColor: Int
        get() = _outlineColor
        set(value) {
            _outlineColor = value
            outlineCirclePaint.color = _outlineColor
            invalidate()
            requestLayout()
        }

    private var _backColor = Color.BLACK
    var backColor: Int
        get() = _backColor
        set(value) {
            _backColor = value
            outlineCirclePaint.color = value
            invalidate()
            requestLayout()
        }

    private val colors: List<Int>
    private var strokeSize = 10f
    private var rect = RectF(0f, 0f, 0f, 0f)
    private val outlineCirclePaint: Paint
    private val backgroundCirclePaint: Paint

    init {
        context?.theme?.obtainStyledAttributes(attrs, R.styleable.ProgressView, 0, 0)?.apply {
            try {
                _progress = getFloat(R.styleable.ProgressView_progress, 0f)
                strokeSize = getDimensionPixelSize(R.styleable.ProgressView_outlineSize, 0).toFloat()
                _outlineColor = getColor(R.styleable.ProgressView_outlineColor, Color.BLUE)
                _backColor = getColor(R.styleable.ProgressView_backColor, Color.BLACK)
            } finally {
                recycle()
            }
        }
        outlineCirclePaint = Paint(ANTI_ALIAS_FLAG).apply {
            this.style = Paint.Style.FILL
            this.color = _outlineColor
        }
        backgroundCirclePaint = Paint(ANTI_ALIAS_FLAG).apply {
            this.style = Paint.Style.FILL
            this.color = _backColor
        }
        colors = resources.getStringArray(R.array.progress_color_short_values)
                .map { Color.parseColor(it) }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rect.set(0f, 0f, w.toFloat(), h.toFloat())
    }

    override fun onDraw(canvas: Canvas) {
        val angle = 360f * _progress
        canvas.drawArc(rect, -90f, angle, true, outlineCirclePaint)
        canvas.drawCircle(rect.width() / 2, rect.height() / 2, (rect.width() / 2) - strokeSize, backgroundCirclePaint)
        super.onDraw(canvas)
    }
}