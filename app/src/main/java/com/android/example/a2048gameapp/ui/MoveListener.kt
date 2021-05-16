package com.android.example.a2048gameapp.ui

/**
 * Author : Shwetha V
 **/
interface MoveListener {
    fun onMove(score: Int, gameOver: Boolean, newSquare: Boolean)
}