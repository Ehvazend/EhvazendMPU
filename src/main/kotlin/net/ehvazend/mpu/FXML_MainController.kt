package net.ehvazend.mpu

import javafx.event.ActionEvent
import javafx.fxml.Initializable
import javafx.scene.Node
import java.net.URL
import java.util.ResourceBundle
import kotlin.concurrent.thread

class FXML_MainController : FXML_MainLogic(), Initializable {
    // Initialization in JavaFX Threat
    override fun initialize(location: URL, resources: ResourceBundle) {
        //Variable late initialization
        bindingCore = StateAssociation(checkBox_Core, titledPane_Core)
        bindingImprovedGraphics = StateAssociation(checkBox_ImprovedGraphics, titledPane_ImprovedGraphics)
        bindingImprovedGraphicsPlus = StateAssociation(checkBox_ImprovedGraphicsPlus, titledPane_ImprovedGraphicsPlus)

        thread {
            refreshInstallPath()
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

    fun button_ChooseDirectory(actionEvent: ActionEvent) {
        callChooseDirectory(defaultPath, modalityWindow = (actionEvent.source as Node).scene.window).also {
            if (it != null) setInstallPath(it)
        }
    }

    fun button_Install() {

    }

    fun checkBox_DefaultPath() {
        setDefaultInstallPath(checkBox_DefaultPath.isSelected)
    }

    fun checkBox_Core() {
        refreshState(checkBox_Core)
    }

    fun checkBox_ImprovedGraphics() {
        refreshState(checkBox_ImprovedGraphics)
    }

    fun checkBox_ImprovedGraphicsPlus() {
        refreshState(checkBox_ImprovedGraphicsPlus)
    }

    fun comboBox_ChangePack() {
        setStatePack(comboBox_Root.value)
    }
}