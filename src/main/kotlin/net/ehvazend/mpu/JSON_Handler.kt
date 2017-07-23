package net.ehvazend.mpu

import com.beust.klaxon.*
import net.ehvazend.mpu.data.JSON_DataMod
import net.ehvazend.mpu.data.JSON_DataModule
import net.ehvazend.mpu.data.JSON_DataPack
import java.net.URL

object JSON_Handler {

    fun loaderFile(name: String): Any {
        return javaClass.getResourceAsStream(name).let { Parser().parse(it) } as Any
    }

    fun loaderURL(name: String): Any {
        return URL(name).openConnection().inputStream.let { Parser().parse(it) } as Any
    }

    fun loaderPack(address: String): ArrayList<JSON_DataPack> {
        val packs = loaderURL(address)

        return ArrayList<JSON_DataPack>().also {
            for (value in packs as JsonArray<*>) {
                (value as JsonObject)

                val pack = JSON_DataPack(
                        value.string("name")!!,
                        value.string("hash")!!,
                        value.string("version")!!,
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

    fun loaderMod(/*hash: String, module: JSON_DataModule*/):ArrayList<JSON_DataMod> {
        // TODO: Realize address find
        val mods = loaderURL("https://raw.githubusercontent.com/Ehvazend/RepositoryMPU/master/Repository/Packs/Interrupt_of_Fall/Core.json")

        return ArrayList<JSON_DataMod>().also {
            for (value in mods as JsonArray<*>) {
                (value as JsonObject)

                it.add(JSON_DataMod( value.string("name")!! ))
            }
        }
    }
}