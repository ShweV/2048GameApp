package com.android.example.a2048gameapp.ui

import android.content.Context
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import com.android.example.a2048gameapp.`interface`.SwipeShift

/**
 * Author : Shwetha V
 **/
class SwipeGestureListener(context : Context,swiper :SwipeShift) : View.OnTouchListener {

    private val SWIPE_THRESHOLD = 100

    private val SWIPE_VELOCITY_THRESHOLD = 100

    companion object {
        var swiper: SwipeShift? = null
        private var gestureDetector: GestureDetector? = null
    }

    init {
        gestureDetector = GestureDetector(context, SwipeGestureListener.GestureListener(swiper))
    }


    fun getGestureDetector(): GestureDetector? {
        return gestureDetector
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
       return true
    }

    private class GestureListener(swiper: SwipeShift) : SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float): Boolean {
            val result = false
            try {
                val diffY = e2.y - e1.y
                val diffX = e2.x - e1.x
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            swiper?.onSwipeRight()
                        } else {
                            swiper?.onSwipeLeft()
                        }
                    }
                } else {
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            swiper?.onSwipeDown()
                        } else {
                            swiper?.onSwipeUp()
                        }
                    }
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
            return result
        }

        companion object {
            private const val SWIPE_THRESHOLD = 100
            private const val SWIPE_VELOCITY_THRESHOLD = 100
        }
    }
}