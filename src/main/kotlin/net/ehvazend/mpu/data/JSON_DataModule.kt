package net.ehvazend.mpu.data

data class JSON_DataModule(val name: String, val state: Boolean, var hash: String? = null, var mods: ArrayList<JSON_DataMod>? = null)