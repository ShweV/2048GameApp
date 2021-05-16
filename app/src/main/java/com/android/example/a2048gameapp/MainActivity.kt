package com.android.example.a2048gameapp

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.example.a2048gameapp.`interface`.SwipeShift
import com.android.example.a2048gameapp.`interface`.onStatDialogClickListener
import com.android.example.a2048gameapp.data.GamePreferences
import com.android.example.a2048gameapp.ui.*

class MainActivity : AppCompatActivity(), View.OnTouchListener {

    private var matrixView: MatrixGridView? = null
    private var currentScore: ScoreView? = null
    private var bestScore: ScoreView? = null
    private var buttonNewGame: Button? = null
    private var swipeListener: SwipeGestureListener? = null
    private var mainLayout: ConstraintLayout? = null
    private var textViewGameTitle: TextView? = null
    private var slideUpAnimation: Animation? = null
    private var textViewLucky: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews();
    }

    override fun onResume() {
        bestScore!!.setScore(GamePreferences.getBestScore())
        super.onResume()
    }

    fun initViews() {
        textViewLucky = findViewById<View>(R.id.main_textview_lucky) as TextView
        slideUpAnimation = AnimationUtils.loadAnimation(this, R.anim.all_the_way_up)
        slideUpAnimation!!.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                textViewLucky!!.setVisibility(View.GONE)
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })

        currentScore = findViewById<ScoreView>(R.id.main_scoreboxview_current)
        bestScore = findViewById<ScoreView>(R.id.main_scoreboxview_best)
        matrixView = findViewById<MatrixGridView>(R.id.matrixGridView)
        matrixView!!.setMoveListener(object : MoveListener{
            override fun onMove(score: Int, gameOver: Boolean, newSquare: Boolean) {
                if (gameOver) {
                    displayGameOverDialog()
                } else {
                    if (!newSquare) {
                        textViewLucky!!.setVisibility(View.VISIBLE)
                        textViewLucky!!.startAnimation(slideUpAnimation)
                    }
                    if (score > 0) {
                        currentScore?.addScore(score)
                        if (currentScore?.getScore()!! > bestScore?.getScore()!!) {
                            bestScore?.setScore(currentScore?.getScore()!!)
                            GamePreferences.saveBestScore(bestScore?.getScore()!!)
                        }
                        if (score >= 2048) {
                            displayCongratsDialog()
                        }
                    }
                }
            }
        })

        buttonNewGame = findViewById<View>(R.id.button_new_game) as Button
        buttonNewGame?.setOnClickListener(View.OnClickListener { onNewGameClick() })

        mainLayout = findViewById<ConstraintLayout>(R.id.main_layout)
        mainLayout?.setOnTouchListener(this)
        swipeListener = SwipeGestureListener(this, object : SwipeShift{
            override fun onSwipeLeft() {
                matrixView?.onSwipeLeft()
            }

            override fun onSwipeRight() {
                matrixView?.onSwipeRight()
            }

            override fun onSwipeUp() {
                matrixView?.onSwipeUp()
            }

            override fun onSwipeDown() {
                matrixView?.onSwipeDown()
            }
        })
    }

    private fun onNewGameClick() {
        matrixView!!.reset()
        currentScore!!.resetScore()
    }

    private fun displayGameOverDialog() {
        val d = StatDialog(this, "Oops, you lost! Try again now?", "Cancel", "New Game")
        d.setOnStatDialogClickListener(object : onStatDialogClickListener {
            override fun onLeftClick() {
                d.dismiss()
            }

            override fun onRightClick() {
                d.dismiss()
                onNewGameClick()
            }
        })
        d.dialog?.show()
    }

    private fun displayCongratsDialog() {
        val d = StatDialog(
            this,
            "Wow, you win! Continue to get a better > 2048?",
            "New Game",
            "Continue"
        )
        d.setOnStatDialogClickListener(object : onStatDialogClickListener {
            override fun onLeftClick() {
                d.dismiss()
                onNewGameClick()
            }

            override fun onRightClick() {
                d.dismiss()
            }
        })
        d.dialog?.show()
    }



    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return swipeListener!!.getGestureDetector()!!.onTouchEvent(event)
    }

}