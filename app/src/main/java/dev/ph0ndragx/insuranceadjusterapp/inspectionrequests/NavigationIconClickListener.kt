package dev.ph0ndragx.insuranceadjusterapp.inspectionrequests

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.animation.Interpolator


class NavigationIconClickListener (
    private val activity: Activity,
    private val sheet: View,
    private val openIcon: View,
    private val closeIcon: View,
    private val frontLayerHeader: View,
    private val interpolator: Interpolator? = null

) {

    private val animatorSet = AnimatorSet()
    private val height: Int
    private var backdropShown = false

    init {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        height = displayMetrics.heightPixels

        openIcon.visibility = View.VISIBLE
        openIcon.alpha = 1f

        closeIcon.visibility = View.GONE
        closeIcon.alpha = 0f

        openIcon.apply {
            setOnClickListener {
                if (!backdropShown) {
                    toggleBackdrop()
                }
            }
        }

        closeIcon.apply {
            setOnClickListener {
                if (backdropShown) {
                    toggleBackdrop()
                }
            }
        }

        frontLayerHeader.apply {
            setOnClickListener {
                if (backdropShown) {
                    toggleBackdrop()
                }
            }
        }
    }

    private fun toggleBackdrop() {
        backdropShown = !backdropShown

        // Cancel the existing animations
        animatorSet.removeAllListeners()
        animatorSet.end()
        animatorSet.cancel()

        animateSheet()
        animateIcons()
    }

    fun animateSheet() {
        val tv = TypedValue()
        activity.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)
        val actionBarHeight = activity.resources.getDimensionPixelSize(tv.resourceId)

        val translateY = sheet.height - actionBarHeight

        val animator = ObjectAnimator.ofFloat(sheet, "translationY", (if (backdropShown) translateY else 0).toFloat())
        animator.duration = 500
        if (interpolator != null) {
            animator.interpolator = interpolator
        }
        animatorSet.play(animator)
        animator.start()
    }

    fun animateIcons() {
        if (backdropShown) {
            closeIcon.animate()
                .alpha(1f)
                .setDuration(500)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator) {
                        closeIcon.visibility = View.VISIBLE
                    }
                })

            openIcon.animate()
                .alpha(0f)
                .setDuration(500)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        openIcon.visibility = View.GONE
                    }
                })
        } else {
            openIcon.animate()
                .alpha(1f)
                .setDuration(500)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator) {
                        openIcon.visibility = View.VISIBLE
                    }
                })

            closeIcon.animate()
                .alpha(0f)
                .setDuration(500)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        closeIcon.visibility = View.GONE
                    }
                })
        }
    }
}
