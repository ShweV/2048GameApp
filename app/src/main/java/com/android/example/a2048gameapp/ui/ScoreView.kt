package com.android.example.a2048gameapp.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.TextView
import com.android.example.a2048gameapp.R

/**
 * Author : Shwetha V
 **/
class ScoreView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    private var score = 0
    private var attrs = attrs
    private var textViewAnim: TextView? = null
    private var textViewLabel: TextView? = null
    private var textViewScore: TextView? = null


    fun getScore(): Int {
        return score
    }

    fun resetScore() {
        score = 0
        textViewScore!!.text = Integer.toString(score)
    }

    fun setScore(score: Int) {
        this.score = score
        textViewScore!!.text = Integer.toString(score)
    }
    init {
        animation = AnimationUtils.loadAnimation(context, R.anim.bubble_up)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                textViewAnim!!.visibility = GONE
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        val inflater = LayoutInflater.from(context)
        val v: View = inflater.inflate(R.layout.score, this)
        textViewAnim = v.findViewById<TextView>(R.id.score_box_textview_animation)
        textViewLabel = v.findViewById<TextView>(R.id.score_box_textview_label)
        textViewScore = v.findViewById<TextView>(R.id.score_box_textview_score)
        textViewScore!!.text = Integer.toString(score)
        parseAttributes(context, this.attrs!!, textViewLabel!!)
    }

    fun addScore(amount: Int) {
        score+= amount
        textViewAnim!!.visibility = VISIBLE
        textViewAnim!!.text = "+$amount"
        textViewAnim!!.startAnimation(animation)
        textViewScore!!.text = Integer.toString(score)
    }
}

private fun parseAttributes(context: Context, attrs: AttributeSet, textViewLabel: TextView) {
    val a = context.theme.obtainStyledAttributes(attrs, R.styleable.ScoreBoxView, 0, 0)
    val label: String?
    try {
        label = a.getString(R.styleable.ScoreBoxView_label_text)
        textViewLabel.text = label
    } finally {
        a.recycle()
    }
}

