package net.ehvazend.mpu

import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.nio.channels.Channels
import java.nio.file.Files
import java.util.*

object FS_Handler {
    fun createDirectory(path: File): String {
        if (path.isDirectory) return "Directory already exists"
        return Files.createDirectory(path.toPath()).toString()
    }

    fun createFile(path: File): String {
        if (path.isFile) return "File already exists"
        return Files.createFile(path.toPath()).toString()
    }

    fun copyCore(directory: File): String {
        val jarPath = File(javaClass.protectionDomain.codeSource.location.path)
        if (!jarPath.isFile) return "Core file not found"
        if (File("$directory\\" + jarPath.name).isFile) return "Core already exists"

        return jarPath.copyTo(File("$directory\\" + jarPath.name)).toString()
    }

    fun loadingFile(address: String, path: String) {
        URL(address).openConnection().also {
            it.getHeaderField("content-disposition")
            FileOutputStream(File(path, StringTokenizer(it.url.file, "/").toList().last() as String))
                    .channel.transferFrom(Channels.newChannel(it.getInputStream()), 0, Long.MAX_VALUE)
        }
    }
}