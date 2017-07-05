package net.ehvazend.mpu

import com.beust.klaxon.Parser
import java.net.HttpURLConnection
import java.net.URL

object HandlerJSON {

    fun parse(name: String) : Any? {
        return javaClass.getResourceAsStream(name).let { Parser().parse(it) }
    }

    fun parseURLAddress(name: String) : Any? {
        val uc = URL(name).openConnection() as HttpURLConnection
        return uc.inputStream.let { Parser().parse(it) }
    }
}
