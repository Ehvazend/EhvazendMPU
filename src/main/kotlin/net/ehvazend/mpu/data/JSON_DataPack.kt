package net.ehvazend.mpu.data


data class JSON_DataPack(var name: String, var version: String, var hash: String, var stateModules: ArrayList<JSON_DataModule>)