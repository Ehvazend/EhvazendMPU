package net.ehvazend.mpu

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.text.Font
import javafx.stage.Stage

class Main : Application() {
    override fun start(primaryStage: Stage) {
        val ROOT = FXMLLoader.load<Parent>(javaClass.getResource("/assets/Main.fxml"))
        val STYLE = javaClass.getResource("/assets/Main.css").toString()
        val FONT = javaClass.getResourceAsStream("/assets/Main.ttf")
        val LOGO = Image(javaClass.getResourceAsStream("/assets/images/logo.png"))
        val TITLE = "Mod Pack Updater"

        // Set scene
        primaryStage.scene = Scene(ROOT, 590.0, 240.0)

        // Scene parameters
        primaryStage.scene.stylesheets.add(STYLE)
        Font.loadFont(FONT, 12.0)
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
}