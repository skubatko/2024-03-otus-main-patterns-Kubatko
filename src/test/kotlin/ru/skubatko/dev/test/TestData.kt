package ru.skubatko.dev.test

import ru.skubatko.dev.hw02.move.Movable
import ru.skubatko.dev.hw02.rotate.Rotatable
import ru.skubatko.dev.hw04.Fueled

abstract class Car : Movable, Fueled

abstract class Bicycle : Movable, Rotatable
