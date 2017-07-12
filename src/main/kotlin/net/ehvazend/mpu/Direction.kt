package net.ehvazend.mpu

enum class Direction(val coordinate: Double, val codeNumber: Int) {
    LEFT(600.0, -1), RIGHT(-600.0, 1)
}