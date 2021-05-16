package com.android.example.a2048gameapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.android.example.a2048gameapp.R
import com.android.example.a2048gameapp.`interface`.onStatDialogClickListener

/**
 * Author : Shwetha V
 **/
class StatDialog(context: Context, msg:String, leftMsg:String, rightMsg:String) : DialogFragment() {
    private var listener: onStatDialogClickListener? = null

    val leftMsg = leftMsg
    val rightMsg = rightMsg
    val msg = msg

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return activity?.layoutInflater?.inflate(R.layout.stat_dialog, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.game_dialog_button_left).setText(leftMsg)
        view.findViewById<TextView>(R.id.game_dialog_button_right).setText(rightMsg)
        view.findViewById<TextView>(R.id.game_dialog_textview_msg).setText(msg)
        view.findViewById<Button>(R.id.game_dialog_button_left).setOnClickListener {
            if (listener!= null) {
                listener!!.onLeftClick()
            }
        }

        view.findViewById<Button>(R.id.game_dialog_button_right).setOnClickListener {
            if (listener!= null) {
                listener!!.onRightClick()
            }
        }


    }

    fun setOnStatDialogClickListener(listener: onStatDialogClickListener?) {
        this.listener = listener
    }


}
