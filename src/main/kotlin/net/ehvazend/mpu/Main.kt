package net.ehvazend.mpu

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage
import java.io.File

class Main : Application() {
    override fun start(primaryStage: Stage) {
        val MODE: Mode = mode()

        val ROOT = FXMLLoader.load<Parent>(javaClass.getResource(MODE.ROOT))
        val STYLE = javaClass.getResource(MODE.STYLE).toString()
        val LOGO = Image(javaClass.getResourceAsStream(MODE.LOGO))
        val TITLE = "MPU: ${MODE.TITLE}"

        // Set scene
        primaryStage.scene = Scene(ROOT, 590.0, 240.0)

        // Scene parameters
        primaryStage.scene.stylesheets.add(STYLE)
        primaryStage.icons.add(LOGO)
        primaryStage.title = TITLE
        primaryStage.isResizable = false

        // Run
        primaryStage.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(Main::class.java)
        }
    }

    // Mode: Install or Update
    fun mode() : Mode {
        val currentPath: File = File("${System.getProperty("user.dir")}\\config.json")
        return if(currentPath.isFile) Mode.UPDATE else Mode.INSTALL
    }

    // Debug version
    fun mode(mode: Mode) : Mode {
        return mode
    }

    enum class Mode(val ROOT: String,
                    val STYLE: String = "/assets/Main.css",
                    val LOGO: String = "/assets/images/logo.png",
                    val TITLE: String) {

        INSTALL(ROOT = "/assets/Main_Install.fxml",TITLE = "Install"),
        UPDATE(ROOT = "/assets/Main_Update.fxml",TITLE = "Update")

    }
}