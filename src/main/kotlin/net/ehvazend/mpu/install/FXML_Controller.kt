package net.ehvazend.mpu.install

import javafx.event.ActionEvent
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.effect.ColorAdjust
import javafx.scene.image.Image
import javafx.stage.Modality
import javafx.stage.Stage
import net.ehvazend.mpu.FXML_Animation
import net.ehvazend.mpu.FXML_Animation.Slider.Direction
import net.ehvazend.mpu.FXML_Animation.Slider.step
import net.ehvazend.mpu.Main
import java.net.URL
import java.util.*
import kotlin.concurrent.thread

class FXML_Controller : FXML_Logic(), Initializable {
    // Initialization in JavaFX Threat
    override fun initialize(location: URL, resources: ResourceBundle) {
        // Variable late initialization
        binding_Core = StateAssociation(checkBox_Core, titledPane_Core)
        binding_ImprovedGraphics = StateAssociation(checkBox_ImprovedGraphics, titledPane_ImprovedGraphics)
        binding_ImprovedGraphicsPlus = StateAssociation(checkBox_ImprovedGraphicsPlus, titledPane_ImprovedGraphicsPlus)

        RootEffect = FXML_Animation.Timeline((rectangle_Root.effect as ColorAdjust).hueProperty(), value = 1.0, duration = 20.0)

        thread(name = "Initialize") {
            RootEffect.play()
            setInstallPath(defaultPath)
            Initialization.slide(VBox_Home, VBox_ModSelection, VBox_Install)
            Initialization.JSON().also { loadEnded(it) }
        }
    }

    fun button_Test() {
        println("Test")
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

    fun button_AddRepository(actionEvent: ActionEvent) {
        RootEffect.pause().also {
            val MODE: Main.Mode = Main.Mode.REPOSITORIES

            val STAGE = Stage()
            val ROOT = FXMLLoader()
            val STYLE = javaClass.getResource(MODE.STYLE).toString()
            val LOGO = Image(javaClass.getResourceAsStream(MODE.LOGO))
            val TITLE = "MPU: ${MODE.TITLE}"

            // Load resources
            ROOT.location = javaClass.getResource(MODE.FXML)
            ROOT.resources = net.ehvazend.mpu.override.ResourceBundle.getBundle("assets.lang.lang", Locale("en"))
            ROOT.resources = net.ehvazend.mpu.override.ResourceBundle.getBundle("assets.lang.lang", Locale("ru"))

            // Modality
            STAGE.initModality(Modality.WINDOW_MODAL)
            STAGE.initOwner((actionEvent.source as Node).scene.window)

            // Set scene
            STAGE.scene = Scene(ROOT.load(), 590.0, 240.0)
            STAGE.style

            // Scene parameters
            STAGE.scene.stylesheets.add(STYLE)
            STAGE.icons.add(LOGO)
            STAGE.title = TITLE
            STAGE.isResizable = false

            // Run
            STAGE.show()
            STAGE.setOnCloseRequest { RootEffect.play() }
        }
    }

    fun checkBox_DefaultPath() {
        setDefaultInstallPath(checkBox_DefaultPath.isSelected)
    }

    fun button_Install() {
        when {
            FXML_Animation.Slider.process -> Unit
            else -> {
                thread {
                    installMode(true)
                    install(currentPath, node = textArea_Install)
                    installMode(false)
                }
            }
        }
    }

    fun checkBox_Core() {
        refreshState(binding_Core)
    }

    fun checkBox_ImprovedGraphics() {
        refreshState(binding_ImprovedGraphics)
    }

    fun checkBox_ImprovedGraphicsPlus() {
        refreshState(binding_ImprovedGraphicsPlus)
    }

    fun comboBox_ChangePack() {
        setState(comboBox_Root.value)
    }
}