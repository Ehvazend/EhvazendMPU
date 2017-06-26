package net.ehvazend.mpu

enum class Direction(val coordinate: Double, val codeNumber: Int) {
    left(600.0, -1), right(-600.0, 1)
}