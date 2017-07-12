package net.ehvazend.mpu

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage
import java.io.File
import java.util.*

class Main : Application() {
    override fun start(primaryStage: Stage) {
        val MODE: Mode = mode()

        val ROOT = FXMLLoader()
        val STYLE = javaClass.getResource(MODE.STYLE).toString()
        val LOGO = Image(javaClass.getResourceAsStream(MODE.LOGO))
        val TITLE = "MPU: ${MODE.TITLE}"

        // Load resources
        ROOT.location = javaClass.getResource(MODE.FXML)
        ROOT.resources = ResourceBundle.getBundle("assets.lang.lang", Locale("en"))
        ROOT.resources = ResourceBundle.getBundle("assets.lang.lang", Locale("ru"))

        // Set scene
        primaryStage.scene = Scene(ROOT.load(), 590.0, 240.0)
        primaryStage.style

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
    fun mode(): Mode {
        val currentPath: File = File("${System.getProperty("user.dir")}\\config.json")
        return if (currentPath.isFile) Mode.UPDATE else Mode.INSTALL
    }

    // Debug version
    fun mode(mode: Mode): Mode {
        return mode
    }

    enum class Mode(val FXML: String,
                    val STYLE: String = "/assets/Main.css",
                    val LOGO: String = "/assets/images/logo.png",
                    val TITLE: String) {

        INSTALL(FXML = "/assets/Main_INSTALL.fxml", TITLE = "Install"),
        UPDATE(FXML = "/assets/Main_UPDATE.fxml", TITLE = "Update")
    }
}