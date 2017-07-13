package net.ehvazend.mpu

import com.beust.klaxon.*
import net.ehvazend.mpu.data.JSON_DataMPU
import net.ehvazend.mpu.data.JSON_DataModule
import net.ehvazend.mpu.data.JSON_DataPack
import java.net.HttpURLConnection
import java.net.URL

object JSON_Handler {

    fun loaderFile(name: String): Any {
        return javaClass.getResourceAsStream(name).let { Parser().parse(it) } as Any
    }

    fun loaderURL(name: String): Any {
        return (URL(name).openConnection() as HttpURLConnection).inputStream.let { Parser().parse(it) } as Any
    }

    // TODO: Do better
    fun dataMPU(): JSON_DataMPU {
        val value = loaderFile("/assets/DataCore.json") as JsonObject
        return JSON_DataMPU(value.string("host")!!, value.string("hashProject")!!, value.string("mode")!!, value.string("nameCoreFile")!!)
    }

    fun address(): String {
        val dataMPU = dataMPU()
        return dataMPU.host + dataMPU.hashProject + dataMPU.mode
    }

    fun packParser(): ArrayList<JSON_DataPack> {
        val address = address()
        val coreJSON = loaderURL(address) as JsonArray<*>

        val list = ArrayList<JSON_DataPack>()

        for (value in coreJSON) {
            value as JsonObject

            val pack = JSON_DataPack(value.string("name")!!, value.string("version")!!, value.string("hash")!!, stateModules = ArrayList())

            for ((key) in value.obj("modules")!!) {
                pack.stateModules.add(JSON_DataModule(key, value.obj("modules")?.boolean(key)!!))
            }

            list.add(pack)
        }

        return list
    }
}
