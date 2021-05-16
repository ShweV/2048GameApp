package com.android.example.a2048gameapp.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.example.a2048gameapp.ui.MatrixGridView
import com.android.example.a2048gameapp.ui.ScoreView


/**
 * Author : Shwetha V
 **/
class ViewModelFactory(matrixView : MatrixGridView, currentScore : ScoreView, context: Context) :
    ViewModelProvider.Factory {
    val matrixView = matrixView
    val currentScore = currentScore
    val context = context
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(matrixView, currentScore, context) as T
        }
    }