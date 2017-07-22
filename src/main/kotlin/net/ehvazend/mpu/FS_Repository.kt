package net.ehvazend.mpu

import java.io.FileNotFoundException
import java.net.URL
import java.util.*

object FS_Repository {
    fun repositories(): ArrayList<String> {
        return ArrayList<String>().also {
            for ((first, second) in Properties().also { it.load(javaClass.getResourceAsStream("/assets/Repositories.properties")) }.toList().reversed()) {
                it.add((first as String).toInt(), second as String)
            }
        }
    }

    fun check(name: String): Boolean {
        try {
            URL(name).openConnection().inputStream
        } catch (exception: FileNotFoundException) {
            return false
        }
        return true
    }
}