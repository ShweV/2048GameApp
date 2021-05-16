package com.android.example.a2048gameapp.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.android.example.a2048gameapp.`interface`.onStatDialogClickListener
import com.android.example.a2048gameapp.ui.MatrixGridView
import com.android.example.a2048gameapp.ui.ScoreView
import com.android.example.a2048gameapp.ui.StatDialog

/**
 * Author : Shwetha V
 **/
class MainViewModel(matrixView : MatrixGridView , currentScore : ScoreView, context: Context) : ViewModel() {
    val matrixView = matrixView
    val currentScore = currentScore
    val context = context

     fun displayGameOverDialog() {
        val d = StatDialog(context, "Oops, you lost! Try again now?", "Cancel", "New Game")
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

     fun displayCongratsDialog() {
        val d = StatDialog(
            context,
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

     fun onNewGameClick() {
        matrixView!!.reset()
        currentScore!!.resetScore()
    }

}