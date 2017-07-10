package net.ehvazend.mpu

import javafx.fxml.Initializable
import java.net.URL
import java.util.ResourceBundle
import kotlin.concurrent.thread


class FXML_MainController : FXML_MainLogic(), Initializable {
    // Initialization in JavaFX Threat
    override fun initialize(location: URL, resources: ResourceBundle) {
        //Variable redefinition
        bindingCore = StateAssociation(checkBox_Core, titledPane_Core)
        bindingImprovedGraphics = StateAssociation(checkBox_ImprovedGraphics, titledPane_ImprovedGraphics)
        bindingImprovedGraphicsPlus = StateAssociation(checkBox_ImprovedGraphicsPlus, titledPane_ImprovedGraphicsPlus)

        thread {
            Initialization.slide(VBox_Home, VBox_ModSelection, VBox_Install)
            Initialization.effect(rectangle_Root)
            Initialization.JSON().also { loadEnded(it) }
        }
    }

    fun button_Test() {
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
        refresh(checkBox_Core)
    }

    fun checkBox_ImprovedGraphics() {
        refresh(checkBox_ImprovedGraphics)
    }

    fun checkBox_ImprovedGraphicsPlus() {
        refresh(checkBox_ImprovedGraphicsPlus)
    }

    fun comboBox_ChangePack() {
        changeState(comboBox_Root.value)
    }
}