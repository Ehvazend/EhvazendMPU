package net.ehvazend.mpu.install

import javafx.event.ActionEvent
import javafx.fxml.Initializable
import javafx.scene.Node
import javafx.scene.effect.ColorAdjust
import net.ehvazend.mpu.FXML_Animation
import net.ehvazend.mpu.FXML_Animation.Slider.Direction
import net.ehvazend.mpu.FXML_Animation.Slider.step
import net.ehvazend.mpu.Repository
import java.net.URL
import java.util.*
import kotlin.concurrent.thread

class FXML_Controller : FXML_Logic(), Initializable {
    // Initialization in JavaFX Threat
    override fun initialize(location: URL, resources: ResourceBundle) {
        //Variable late initialization
        bindingCore = StateAssociation(checkBox_Core, titledPane_Core)
        bindingImprovedGraphics = StateAssociation(checkBox_ImprovedGraphics, titledPane_ImprovedGraphics)
        bindingImprovedGraphicsPlus = StateAssociation(checkBox_ImprovedGraphicsPlus, titledPane_ImprovedGraphicsPlus)
        RootEffect = FXML_Animation.Timeline((rectangle_Root.effect as ColorAdjust).hueProperty(), value = 1.0, duration = 20.0)

        thread(name = "Initialize") {
            RootEffect.play()
            setInstallPath(defaultPath)
            Initialization.slide(VBox_Home, VBox_ModSelection, VBox_Install)
            Initialization.JSON().also { loadEnded(it) }
        }
    }

    fun button_Test() {
        Repository.repositories()
    }

    fun button_Next() {
        step(Direction.RIGHT)
    }

    fun button_Past() {
        step(Direction.LEFT)
    }

    fun button_ChooseDirectory(actionEvent: ActionEvent) {
        RootEffect.pause()
        callChooseDirectory(defaultPath, modalityWindow = (actionEvent.source as Node).scene.window).also {
            if (it != null) setInstallPath(it)
        }
        RootEffect.play()
    }

    fun button_AddRepository() {

    }

    fun checkBox_DefaultPath() {
        setDefaultInstallPath(checkBox_DefaultPath.isSelected)
    }

    fun button_Install() {

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