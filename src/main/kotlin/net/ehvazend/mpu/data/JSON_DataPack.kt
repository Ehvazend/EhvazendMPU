package net.ehvazend.mpu.data

data class JSON_DataPack(var repository: String, var name: String, var hashName: String, var version: String, var hashVersion: String, var stateModules: ArrayList<JSON_DataModule>)