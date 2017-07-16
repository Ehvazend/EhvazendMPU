package net.ehvazend.mpu

import java.util.*

object Repository {
    fun repositories(): ArrayList<String> {
        return ArrayList<String>().also {
            for ((first, second) in Properties().also { it.load(javaClass.getResourceAsStream("/assets/Repositories.properties")) }.toList().reversed()) {
                it.add((first as String).toInt(), second as String)
            }
        }
    }
}