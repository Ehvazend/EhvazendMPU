package net.ehvazend.mpu

import javafx.fxml.Initializable
import java.io.File
import java.net.URL
import java.util.ResourceBundle
import kotlin.concurrent.thread


class FXML_MainController : FXML_MainLogic(), Initializable {
    private val defaultPath = File("${System.getProperty("user.home")}\\AppData\\Roaming\\$NAME")
    private var currentPath = defaultPath
    private var pastPath = defaultPath

    override fun initialize(location: URL, resources: ResourceBundle) {
        thread {
            Initialization.slide(VBox_Home, VBox_ModSelection, VBox_Install)
            Initialization.effect(rectangle_Root)
            Initialization.JSON().also { loadEnded(it) }
        }
    }

    fun button_Next() {
        FXML_Animation.slider(Direction.right)
    }

    fun button_Past() {
        FXML_Animation.slider(Direction.left)
    }

    fun button_Install() {

    }

    fun button_ChooseDirectory() {

    }

    fun checkBox_DefaultPath() {

    }

    fun checkBox_Core() {

    }

    fun checkBox_ImprovedGraphics() {

    }

    fun checkBox_ImprovedGraphicsPlus() {

    }

    fun comboBox_ChangePack() {
        changeState(comboBox_Root.value)
    }
}