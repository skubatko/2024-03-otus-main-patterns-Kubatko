package ru.skubatko.dev.hw01

import java.lang.Double.NEGATIVE_INFINITY
import java.lang.Double.NaN
import java.lang.Double.POSITIVE_INFINITY
import kotlin.math.abs
import kotlin.math.sqrt

class QuadraticEquation {

    companion object {
        fun solve(a: Double, b: Double, c: Double, eps: Double = 1e-7): DoubleArray {
            if (listOf(a, b, c).contains(POSITIVE_INFINITY)) {
                throw IllegalArgumentException()
            }
            if (listOf(a, b, c).contains(NEGATIVE_INFINITY)) {
                throw IllegalArgumentException()
            }
            if (listOf(a, b, c).contains(NaN)) {
                throw IllegalArgumentException()
            }

            if (abs(a) <= eps) {
                throw IllegalArgumentException("a не должно быть равно 0")
            }

            val discriminant = b * b - 4 * a * c
            if (discriminant < -eps) {
                return DoubleArray(0)
            }

            if (abs(discriminant) <= eps) {
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
