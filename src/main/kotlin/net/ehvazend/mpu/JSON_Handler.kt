package net.ehvazend.mpu

import com.beust.klaxon.*
import java.net.HttpURLConnection
import java.net.URL

object JSON_Handler {

    fun parser(name: String): JsonObject {
        return javaClass.getResourceAsStream(name).let { Parser().parse(it) } as JsonObject
    }

    fun parserURLAddress(name: String): Any? {
        val uc = URL(name).openConnection() as HttpURLConnection
        return uc.inputStream.let { Parser().parse(it) }
    }

    fun dataMPU(): JSON_DataMPU {
        val value = parser("/assets/Data.json")
        return JSON_DataMPU(host = value.string("host")!!, hashProject = value.string("hashProject")!!, mode = value.string("mode")!!, nameCoreFile = value.string("nameCoreFile")!!)
    }

    fun address(): String {
        val dataMPU = dataMPU()
        return dataMPU.host + dataMPU.hashProject + dataMPU.mode
    }

    fun packParser(): ArrayList<JSON_DataPack> {
        val address = address()
        val coreJSON = parserURLAddress(address) as JsonArray<*>

        val list = ArrayList<JSON_DataPack>()

        for (value in coreJSON) {
            value as JsonObject

            val pack = JSON_DataPack(name = value.string("name")!!, version = value.string("version")!!, hash = value.string("hash")!!, stateModules = ArrayList())

            for ((key) in value.obj("modules")!!) {
                pack.stateModules.add(JSON_DataModule(key, value.obj("modules")?.boolean(key)!!))
            }

            list.add(pack)
        }

        return list
    }
}
