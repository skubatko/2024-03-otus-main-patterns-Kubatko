package ru.skubatko.dev.hw01

import kotlin.math.sqrt

class QuadraticEquation {

    companion object {
        fun solve(a: Double, b: Double, c: Double): DoubleArray {
            val delta = b * b - 4 * a * c
            if (delta < 0) {
                return DoubleArray(0)
            }

            val d = sqrt(delta)
            val result = DoubleArray(2)
            result[0] = (-b + d) / 2 / a
            result[1] = (-b - d) / 2 / a
            return result
        }
    }
}
