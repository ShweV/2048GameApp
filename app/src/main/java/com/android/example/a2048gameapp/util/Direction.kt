package com.android.example.a2048gameapp.util

/**
 * Author : Shwetha V
 **/
enum class Direction(x: Int, y: Int, begin: Int, end: Int, shift: Int) {
    UP(+0, -1, Matrix.N, -1, -1),
    DOWN(+0, +1, -1, Matrix.N, +1),
    LEFT(-1, +0, Matrix.N, -1, -1),
    RIGHT(+0, +1, -1, Matrix.N, +1);

    val x: Int
    val y: Int
    val begin: Int
    val end: Int
    val shift: Int

    init {
        this.x = x
        this.y = y
        this.begin = begin
        this.end = end
        this.shift = shift
    }
}