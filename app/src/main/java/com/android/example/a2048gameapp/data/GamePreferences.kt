package com.android.example.a2048gameapp.data

import android.content.Context
import android.content.SharedPreferences

/**
 * Author : Shwetha V
 **/
class GamePreferences {

    internal enum class Key {
        BEST_SCORE
    }

    companion object {
        private var prefs: SharedPreferences? = null
        fun initialize(context: Context) {
            if (prefs== null) {
                prefs = context.getSharedPreferences("2048.prefs_", Context.MODE_PRIVATE)
            }
        }

        fun saveBestScore(score: Int) {
            prefs!!.edit().putInt(Key.BEST_SCORE.name, score).commit()
        }

        fun getBestScore(): Int {
            return prefs!!.getInt(Key.BEST_SCORE.name, 0)
        }
    }
}