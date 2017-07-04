package net.ehvazend.mpu

import javafx.scene.control.TextInputControl
import java.io.File
import java.nio.file.Files

object Install {
    fun installMPU(directory: File, catchingMode: Boolean = false, node: TextInputControl? = null): Boolean {
        fun concatenationPath(nameFile: String): File {
            return File("$directory\\" + nameFile)
        }

        fun catchingValue(value: String) {
            if (catchingMode && node != null) node.appendText(value + "\n")
        }

        catchingValue(installDirectory(directory))
        catchingValue(installFile(concatenationPath("config.json")))
        catchingValue(copyCore(directory))

        return true
    }

    private fun installDirectory(path: File): String {
        if (path.isDirectory) return "Directory already exists"
        return Files.createDirectory(path.toPath()).toString()
    }

    private fun installFile(path: File): String {
        if (path.isFile) return "File already exists"
        return Files.createFile(path.toPath()).toString()
    }

    private fun copyCore(directory: File): String {
        val jarPath = File(javaClass.protectionDomain.codeSource.location.path)
        if (!jarPath.isFile) return "Core file not found"
        if (File("$directory\\" + jarPath.name).isFile) return "Core already exists"

        return jarPath.copyTo(File("$directory\\" + jarPath.name)).toString()
    }
}