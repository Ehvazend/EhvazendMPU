package net.ehvazend.mpu.data

data class JSON_DataPack(var repository: String, var name: String, var hash: String, var version: String, var stateModules: ArrayList<JSON_DataModule>)