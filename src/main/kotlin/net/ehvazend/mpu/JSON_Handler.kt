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

    fun loaderCore(): JSON_DataMPU {
        return (loaderFile("/assets/DataCore.json") as JsonObject).let {
            JSON_DataMPU(
                    it.string("host")!!,
                    it.string("hashProject")!!,
                    it.string("mode")!!,
                    it.string("nameCoreFile")!!
            )
        }
    }

    fun loaderPack(): ArrayList<JSON_DataPack> {
        val packs = loaderURL(loaderCore().let {
            it.host + it.hashProject + it.mode
        })

        return ArrayList<JSON_DataPack>().also {
            for (value in packs as JsonArray<*>) {
                (value as JsonObject)

                val pack = JSON_DataPack(
                        value.string("name")!!,
                        value.string("version")!!,
                        value.string("hash")!!,
                        stateModules = ArrayList()
                )

                for ((key) in value.obj("modules") as JsonObject) {
                    pack.stateModules.add(JSON_DataModule(
                            name = key,
                            state = (value.obj("modules") as JsonObject).boolean(key)!!
                    ))
                }

                it.add(pack)
            }
        }
    }
}