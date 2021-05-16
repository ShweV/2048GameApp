package com.android.example.a2048gameapp.util

/**
 * Author : Shwetha V
 **/
class Validate {
    fun mustBeTrue(condition: Boolean, msg: String) {
        if (!condition) {
            throw RuntimeException("Condition is not true, $msg")
        }
    }
}