package ru.skubatko.dev.hw01

import kotlin.math.abs
import kotlin.math.sqrt

class QuadraticEquation {

    companion object {
        fun solve(a: Double, b: Double, c: Double): DoubleArray {
            val eps = 0.001
            if (abs(a) < eps) {
                throw IllegalArgumentException()
            }

            val discriminant = b * b - 4 * a * c
            if (discriminant < 0) {
                return DoubleArray(0)
            }

            if (discriminant < eps) {
                val result = DoubleArray(1)
                result[0] = -b / 2 / a
                return result
            }

            val d = sqrt(discriminant)
            val result = DoubleArray(2)
            result[0] = (-b + d) / 2 / a
            result[1] = (-b - d) / 2 / a
            return result
        }
    }
}
