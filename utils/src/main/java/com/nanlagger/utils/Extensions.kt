package com.nanlagger.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.view.ViewCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun Resources.color(@ColorRes colorRes: Int, theme: Resources.Theme? = null): Int {
    return ResourcesCompat.getColor(this, colorRes, theme)
}

fun Context.color(@ColorRes colorRes: Int): Int {
    return ContextCompat.getColor(this, colorRes)
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun View.visibleOrGone(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.visibleOrInvisible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

fun View.setColorFilter(@ColorRes color: Int) {
    val colorInt = ContextCompat.getColor(context, color)

    val drawable: Drawable = (this as? ImageView)?.drawable ?: background.mutate()

    when (drawable) {
        is ShapeDrawable -> drawable.paint.color = colorInt
        is GradientDrawable -> drawable.setColor(colorInt)
        is ColorDrawable -> drawable.color = colorInt
        else -> drawable.setColorFilter(colorInt, PorterDuff.Mode.SRC_ATOP)
    }

    if (this is ImageView)
        setImageDrawable(drawable)
    else
        background = drawable
}

fun View.elevation(elevation: Float) {
    ViewCompat.setElevation(this, elevation)
}

fun View.showKeyboard() {
    if (this.requestFocus()) {
        val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun View.hideKeyboard() {
    val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun Context.dpFromPx(px: Float): Float = px / this.resources.displayMetrics.density

fun Context.pxFromDp(dp: Float): Float = dp * this.resources.displayMetrics.density

fun Disposable.addTo(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}

fun <T> Observable<T>.async(): Observable<T> = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun <T> Flowable<T>.async(): Flowable<T> = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.async(): Single<T> = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun <T> Maybe<T>.async(): Maybe<T> = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun Completable.async(): Completable = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun Resources.drawable(@DrawableRes id: Int, theme: Resources.Theme? = null): Drawable? {
    return ResourcesCompat.getDrawable(this, id, theme)
}
