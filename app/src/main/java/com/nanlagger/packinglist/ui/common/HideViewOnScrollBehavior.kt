package com.nanlagger.packinglist.ui.common

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TimeInterpolator
import android.content.Context
import android.support.design.animation.AnimationUtils
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.View
import android.view.ViewPropertyAnimator



class HideViewOnScrollBehavior<V : View>(context: Context, attrs: AttributeSet): CoordinatorLayout.Behavior<V>(context, attrs) {

    protected val ENTER_ANIMATION_DURATION = 225L
    protected val EXIT_ANIMATION_DURATION = 175L
    private val STATE_SCROLLED_DOWN = 1
    private val STATE_SCROLLED_UP = 2
    private var height = 0
    private var marginBottom = 0
    private var currentState = 2
    private var currentAnimator: ViewPropertyAnimator? = null

    override fun onLayoutChild(parent: CoordinatorLayout, child: V, layoutDirection: Int): Boolean {
        this.height = child.measuredHeight
        val layoutParams = child.layoutParams
        if (layoutParams is CoordinatorLayout.LayoutParams) {
            marginBottom = layoutParams.bottomMargin
        }
        return super.onLayoutChild(parent, child, layoutDirection)
    }

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: V, directTargetChild: View, target: View, nestedScrollAxes: Int, type: Int): Boolean {
        return nestedScrollAxes == 2
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: V, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        if (this.currentState != STATE_SCROLLED_DOWN && dyConsumed > 0) {
            this.slideDown(child)
        } else if (this.currentState != STATE_SCROLLED_UP && dyConsumed < 0) {
            this.slideUp(child)
        }

    }

    protected fun slideUp(child: V) {
        if (this.currentAnimator != null) {
            this.currentAnimator!!.cancel()
            child.clearAnimation()
        }

        this.currentState = STATE_SCROLLED_UP
        this.animateChildTo(child, 0, ENTER_ANIMATION_DURATION, AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR)
    }

    protected fun slideDown(child: V) {
        if (this.currentAnimator != null) {
            this.currentAnimator!!.cancel()
            child.clearAnimation()
        }

        this.currentState = STATE_SCROLLED_DOWN
        this.animateChildTo(child, this.height + marginBottom, EXIT_ANIMATION_DURATION, AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR)
    }

    private fun animateChildTo(child: V, targetY: Int, duration: Long, interpolator: TimeInterpolator) {
        this.currentAnimator = child.animate().translationY(targetY.toFloat()).setInterpolator(interpolator).setDuration(duration).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                this@HideViewOnScrollBehavior.currentAnimator = null
            }
        })
    }


}

