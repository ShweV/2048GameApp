package com.android.example.a2048gameapp.util

import android.util.Log
import java.util.*

/**
 * Author : Shwetha V
 **/
class Matrix {
    companion object {

        val EMPTY = 0

        val N = 4
    }

    private lateinit var numbers: Array<IntArray>

    private var random: Random? = null

    private var emptySpots: MutableList<Spot>? = null

    private var mergeSpots: MutableSet<Spot>? = null

    private var newSpot: Spot? = null

    init {
        random = Random()
        numbers = Array(N) { IntArray(N) }
        for (i in 0 until N) {
            for (j in 0 until N) {
                numbers[i][j] = EMPTY
            }
        }
        emptySpots = ArrayList()
        mergeSpots = HashSet()
        newSpot = Spot(0, 0)

        // Generate 2 random spots
        for (i in 0..4) {
            spawn(2)
        }

    }

    private fun generateAll() {
        spawn(2)
        spawn(4)
        spawn(8)
        spawn(16)
        spawn(32)
        spawn(64)
        spawn(128)
        spawn(256)
        spawn(512)
        spawn(1024)
        spawn(2048)
    }

    /**
     * Generate a square with the distribution:
     * 80% = 2
     * 15% = 4
     * 5% = 'empty'
     */
    fun generate(): Boolean {
        val v = random!!.nextInt(100)
        if (0 <= v && v <= 79) {
            spawn(2)
            return true
        } else if (80 <= v && v <= 95) {
            spawn(4)
            return true
        }
        return false
    }

    fun Matrix(copy: Matrix) {
        random = copy.random
        numbers = Array(N) { IntArray(N) }
        for (r in 0 until N) {
            for (c in 0 until N) {
                numbers[r][c] = copy.numbers.get(r).get(c)
            }
        }
        emptySpots = ArrayList()
        for (spot in copy.emptySpots!!) {
            (emptySpots as ArrayList<Spot>).add(spot)
        }
        mergeSpots = HashSet()
        for (spot in copy.mergeSpots!!) {
            (mergeSpots as HashSet<Spot>).add(spot)
        }
        newSpot = Spot(copy.newSpot!!.r, copy.newSpot!!.c)
    }

    fun getSpot(r: Int, c: Int): Int {
        return numbers[r][c]
    }

    private fun spawn(n: Int) {
        collectEmptySpots()
        if (!emptySpots!!.isEmpty()) {
            val i = random!!.nextInt(emptySpots!!.size)
            newSpot = emptySpots!![i]
            numbers[newSpot!!.r][newSpot!!.c] = n
        }
    }

    private fun isPowerOfTwo(n: Int): Boolean {
        return if (n < 1) false else n and n - 1 == 0
    }

    fun getNewSpot(): Spot? {
        return newSpot
    }

    private fun collectEmptySpots() {
        emptySpots!!.clear()
        for (x in 0 until N) {
            for (y in 0 until N) {
                if (numbers[x][y] == EMPTY) {
                    emptySpots!!.add(Spot(x, y))
                }
            }
        }
    }

    fun isStuck(): Boolean {
        Log.e("2048", "Hello")
        val copy: Matrix = this
        var score = 0
        for (d in Direction.values()) {
            score += copy.swipe(d)
        }
        copy.collectEmptySpots()
        Log.e("2048", "Hello end()")
        return score == 0 && copy.emptySpots!!.isEmpty()
    }

    fun mergeLeft(row: Int): Int {
        var idx = 0
        var score = 0
        var merged = false
        for (i in 0 until N) {
            if (numbers[row][i] != EMPTY) {
                // Find the farthest to the right of i
                var farthest = -1
                for (j in i + 1 until N) {
                    if (numbers[row][j] != EMPTY) {
                        farthest = j
                        break
                    }
                }
                merged = false
                if (farthest != -1) {
                    // Ok merge
                    if (numbers[row][i] == numbers[row][farthest]) {
                        numbers[row][i] += numbers[row][farthest]
                        score += numbers[row][i]
                        numbers[row][farthest] = EMPTY
                        merged = true
                    }
                }
                numbers[row][idx] = numbers[row][i]
                if (merged) {
                    mergeSpots!!.add(Spot(row, idx))
                }
                if (idx != i) {
                    numbers[row][i] = EMPTY
                }
                idx++
            }
        }
        return score
    }

    fun mergeRight(row: Int): Int {
        var idx = N - 1
        var score = 0
        var merged = false
        for (i in N - 1 downTo 0) {
            if (numbers[row][i] != EMPTY) {
                // Find the farthest to the right of i
                var farthest = -1
                for (j in i - 1 downTo 0) {
                    if (numbers[row][j] != EMPTY) {
                        farthest = j
                        break
                    }
                }
                merged = false
                if (farthest != -1) {
                    // Ok merge
                    if (numbers[row][i] == numbers[row][farthest]) {
                        numbers[row][i] += numbers[row][farthest]
                        score += numbers[row][i]
                        numbers[row][farthest] = EMPTY
                        merged = true
                    }
                }
                numbers[row][idx] = numbers[row][i]
                if (merged) {
                    mergeSpots!!.add(Spot(row, idx))
                }
                if (idx != i) {
                    numbers[row][i] = EMPTY
                }
                idx--
            }
        }
        return score
    }

    fun mergeUp(column: Int): Int {
        var idx = 0
        var score = 0
        var merged = false
        for (i in 0 until N) {
            if (numbers[i][column] != EMPTY) {
                // Find the farthest to the right of i
                var farthest = -1
                for (j in i + 1 until N) {
                    if (numbers[j][column] != EMPTY) {
                        farthest = j
                        break
                    }
                }
                merged = false
                if (farthest != -1) {
                    // Ok merge
                    if (numbers[i][column] == numbers[farthest][column]) {
                        numbers[i][column] += numbers[farthest][column]
                        score = numbers[i][column]
                        numbers[farthest][column] = EMPTY
                        merged = true
                    }
                }
                numbers[idx][column] = numbers[i][column]
                if (merged) {
                    mergeSpots!!.add(Spot(idx, column))
                }
                if (idx != i) {
                    numbers[i][column] = EMPTY
                }
                idx++
            }
        }
        return score
    }

    fun mergeDown(column: Int): Int {
        var idx = N - 1
        var score = 0
        var merged: Boolean
        for (i in N - 1 downTo 0) {
            if (numbers[i][column] != EMPTY) {
                // Find the farthest to the right of i
                var farthest = -1
                for (j in i - 1 downTo 0) {
                    if (numbers[j][column] != EMPTY) {
                        farthest = j
                        break
                    }
                }
                merged = false
                if (farthest != -1) {
                    // Ok merge
                    if (numbers[i][column] == numbers[farthest][column]) {
                        numbers[i][column] += numbers[farthest][column]
                        score = numbers[i][column]
                        numbers[farthest][column] = EMPTY
                        merged = true
                    }
                }
                numbers[idx][column] = numbers[i][column]
                if (merged) {
                    mergeSpots!!.add(Spot(idx, column))
                }
                if (idx != i) {
                    numbers[i][column] = EMPTY
                }
                idx--
            }
        }
        return score
    }

    fun getNumbers(): Array<IntArray>? {
        return numbers
    }

    fun isMergeSpot(r: Int, c: Int): Boolean {
        return mergeSpots!!.contains(Spot(r, c))
    }

    private fun cloneRow(r: Int): IntArray? {
        val copy = IntArray(4)
        for (i in 0 until N) {
            copy[i] = numbers[r][i]
        }
        return copy
    }

    private fun cloneColumn(c: Int): IntArray? {
        val copy = IntArray(4)
        for (i in 0 until N) {
            copy[i] = numbers[i][c]
        }
        return copy
    }

    private fun isSame(before: IntArray, after: IntArray): Boolean {
        for (i in 0 until N) {
            if (before[i] != after[i]) {
                return false
            }
        }
        return true
    }

    private fun cloneNumbers(): Array<IntArray>? {
        val copy = Array(N) { IntArray(N) }
        for (r in 0 until N) {
            for (c in 0 until N) {
                copy[r][c] = numbers[r][c]
            }
        }
        return copy
    }

    fun swipe(dir: Direction): Int {
        var totalScore = 0
        mergeSpots!!.clear()
        if (dir === Direction.DOWN || dir === Direction.UP) {
            for (i in 0 until N) {
                totalScore += if (dir === Direction.DOWN) mergeDown(i) else mergeUp(i)
            }
        } else {
            for (i in 0 until N) {
                totalScore += if (dir === Direction.LEFT) mergeLeft(i) else mergeRight(i)
            }
        }
        return totalScore
    }

    class Spot(val r: Int, val c: Int) {
        override fun equals(o: Any?): Boolean {
            if (this === o) return true
            if (o !is Spot) return false
            val spot = o
            if (c != spot.c) return false
            return if (r != spot.r) false else true
        }

        override fun hashCode(): Int {
            var result = r
            result = 31 * result + c
            return result
        }
    }

    fun setSpot(r: Int, c: Int, value: Int) {
        numbers[r][c] = value
    }

    override fun toString(): String {
        val builder = StringBuilder()
        for (r in 0 until N) {
            for (c in 0 until N) {
                builder.append("|")
                builder.append(Integer.toString(numbers[r][c]))
                builder.append("|")
            }
            builder.append("\n")
        }
        return builder.toString()
    }
}