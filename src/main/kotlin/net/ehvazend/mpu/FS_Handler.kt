package net.ehvazend.mpu

import java.io.File
import java.nio.file.Files

object FS_Handler {
    private fun createDirectory(path: File): String {
        if (path.isDirectory) return "Directory already exists"
        return Files.createDirectory(path.toPath()).toString()
    }

    private fun createFile(path: File): String {
        if (path.isFile) return "File already exists"
        return Files.createFile(path.toPath()).toString()
    }

    private fun copyCore(directory: File): String {
        val jarPath = File(javaClass.protectionDomain.codeSource.location.path)
        if (!jarPath.isFile) return "Core file not found"
        if (File("$directory\\" + jarPath.name).isFile) return "Core already exists"

        return jarPath.copyTo(File("$directory\\" + jarPath.name)).toString()
    }

    fun installMPU(/*directory: File, catchingMode: Boolean = false, node: TextInputControl? = null*/)/*: Boolean*/ {
//        fun concatenationPath(nameFile: String): File {
//            return File("$directory\\" + nameFile)
//        }
//
//        fun separationPath(path: String): String {
//            return path.replace(directory.toString(), "")
//        }
//
//        fun catchingValue(value: String, modeShort: Boolean = false) {
//            if (catchingMode && node != null && !modeShort) node.appendText(value + "\n")
//            if (catchingMode && node != null && modeShort) node.appendText(separationPath(value) + "\n")
//        }
//
//        catchingValue(createDirectory(directory))
//        catchingValue(createFile(concatenationPath("config.json")), true)
//        catchingValue(copyCore(directory), true)
//
//        return true
    }
}