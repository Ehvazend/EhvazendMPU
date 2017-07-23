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

                // load modules
                for (module in value.array<Any>("modules") as JsonArray) {
                    (module as JsonObject)

                    module.keys.first().also {
                        pack.stateModules.add(JSON_DataModule(it, module[it] as Boolean).also {
                            when {
                                module[it.name] as Boolean -> it.hash = module.string("hash")
                            }
                        })
                    }
                }

                it.add(pack)
            }
        }
    }

    fun loaderMod(
            repository: String = "https://raw.githubusercontent.com/Ehvazend/RepositoryMPU/master/Repository/",
            hash: String = "Interrupt_of_Fall",
            module: JSON_DataModule = JSON_DataModule(name = "core", state = true, hash = "Core.json")): ArrayList<JSON_DataMod> {
        val mods = loaderURL(repository + "Packs/$hash/${module.hash}")

        return ArrayList<JSON_DataMod>().also {
            for (value in mods as JsonArray<*>) {
                (value as JsonObject)

                it.add(JSON_DataMod(value.string("name")!!))
            }
        }
    }
}