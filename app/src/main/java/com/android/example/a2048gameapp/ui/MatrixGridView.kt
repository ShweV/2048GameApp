package com.android.example.a2048gameapp.ui

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TableLayout
import com.android.example.a2048gameapp.R
import com.android.example.a2048gameapp.`interface`.SwipeShift
import com.android.example.a2048gameapp.util.Direction
import com.android.example.a2048gameapp.util.Matrix

/**
 * Author : Shwetha V
 **/
class MatrixGridView(context: Context?, attrs: AttributeSet?) : TableLayout(context, attrs), SwipeShift {

    private val N: Int = Matrix.N

    private val borderPaint: Paint? = null

    private val textPaint: Paint? = null

    private val squarePaint: Paint? = null

    private var matrix: Matrix? = null

    private val borderThickness = 10

    private val velocityTracker: VelocityTracker? = null

    private lateinit var tiles: Array<Array<Button?>>

    private var leftAnimation: Animation? = null

    private var rightAnimation: Animation? = null

    private var upAnimation: Animation? = null

    private var downAnimation: Animation? = null

    private var rotateAnimation: Animation? = null

    private var appearingAnimation: Animation? = null

    private var swipeListener: SwipeGestureListener? = null

    private var moveListener: MoveListener? = null

    private var moreMove = true

    

    init {
        val inflater = LayoutInflater.from(context)
        val v: View = inflater.inflate(R.layout.matrix_grid, this)
        // Initialize all buttons
        tiles = Array(N) {
            arrayOfNulls(N)
        }

        tiles[0][0] = v.findViewById(R.id.button00)
        tiles[0][1] = v.findViewById(R.id.button01)
        tiles[0][2] = v.findViewById(R.id.button02)
        tiles[0][3] = v.findViewById(R.id.button03)
        tiles[1][0] = v.findViewById(R.id.button10)
        tiles[1][1] = v.findViewById(R.id.button11)
        tiles[1][2] = v.findViewById(R.id.button12)
        tiles[1][3] = v.findViewById(R.id.button13)
        tiles[2][0] = v.findViewById(R.id.button20)
        tiles[2][1] = v.findViewById(R.id.button21)
        tiles[2][2] = v.findViewById(R.id.button22)
        tiles[2][3] = v.findViewById(R.id.button23)
        tiles[3][0] = v.findViewById(R.id.button30)
        tiles[3][1] = v.findViewById(R.id.button31)
        tiles[3][2] = v.findViewById(R.id.button32)
        tiles[3][3] = v.findViewById(R.id.button33)
        leftAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_left)
        rightAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_right)
        upAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_up)
        downAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_down)
        rotateAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate)
        appearingAnimation = AnimationUtils.loadAnimation(context, R.anim.appearing)
        matrix = Matrix()
        displayMatrix(matrix, Direction.DOWN)
        swipeListener = SwipeGestureListener(context!!, this)
    }

    fun reset() {
        matrix = Matrix()
        displayMatrix(matrix, Direction.DOWN)
        moreMove = true
    }

    fun setMoveListener(listener: MoveListener?) {
        moveListener = listener
    }

    fun hasMoreMove(): Boolean {
        return moreMove
    }

    private fun displayMatrix(m: Matrix?, dir: Direction) {
        val n: Int = Matrix.N
        var number: Int
        for (r in 0 until n) {
            for (c in 0 until n) {
                number = m!!.getSpot(r, c)
                if (number == Matrix.EMPTY) {
                    tiles[r][c]!!.text = ""
                } else {
                    tiles[r][c]!!.text = Integer.toString(number)
                }
                tiles[r][c]!!.background = resources.getDrawable(getDrawableId(number), null)
                if (matrix!!.isMergeSpot(r, c)) {
                    tiles[r][c]!!.startAnimation(appearingAnimation)
                }
                 applyEffect(tiles[r][c]!!, dir);
            }
        }
        invalidate()
    }

    private fun isEdge(r: Int, c: Int): Boolean {
        return r == 0 || r == N - 1 || c == 0 || c == N - 1
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        swipeListener?.getGestureDetector()?.onTouchEvent(event)
        return super.dispatchTouchEvent(event)
    }

    override fun onSwipeUp() {
        Log.e("2048", "swipe up")
        handleSwipe(Direction.UP)
    }

    override fun onSwipeRight() {
        Log.e("2048", "swipe right")
        handleSwipe(Direction.RIGHT)
    }

    override fun onSwipeLeft() {
        Log.e("2048", "swipe left")
        handleSwipe(Direction.LEFT)
    }

    override fun onSwipeDown() {
        Log.e("2048", "swipe down")
        handleSwipe(Direction.DOWN)
    }

    private fun handleSwipe(dir: Direction) {
        val score: Int = matrix!!.swipe(dir)
        val gameOver: Boolean = matrix!!.isStuck()
        val newSquare: Boolean = matrix!!.generate()
        displayMatrix(matrix, dir)
        moveListener!!.onMove(score, gameOver, newSquare)
    }

    private fun applyEffect(button: Button, dir: Direction) {
        if (dir === Direction.DOWN) {
            button.startAnimation(downAnimation)
            button.invalidate()
        } else if (dir === Direction.UP) {
            button.startAnimation(upAnimation)
            button.invalidate()
        } else if (dir === Direction.LEFT) {
            button.startAnimation(leftAnimation)
            button.invalidate()
        } else if (dir === Direction.RIGHT) {
            button.startAnimation(rightAnimation)
            button.invalidate()
        }
    }

    private fun getTextSize(n: Int): Int {
        return 18
    }

    private fun getDrawableId(n: Int): Int {
        when (n) {
            2 -> return R.drawable.n_2
            4 -> return R.drawable.n_4
            8 -> return R.drawable.n_8
            16 -> return R.drawable.n_16
            32 -> return R.drawable.n_32
            64 -> return R.drawable.n_64
            128 -> return R.drawable.n_128
            256 -> return R.drawable.n_256
            512 -> return R.drawable.n_512
            1024 -> return R.drawable.n_1024
            2048 -> return R.drawable.n_2048
        }
        return R.drawable.n_0
    }
}