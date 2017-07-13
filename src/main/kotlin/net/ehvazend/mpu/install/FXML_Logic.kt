package net.ehvazend.mpu.install

import javafx.application.Platform
import javafx.scene.Group
import javafx.scene.Node
import javafx.scene.control.CheckBox
import javafx.scene.control.TextInputControl
import javafx.stage.DirectoryChooser
import javafx.stage.Modality
import javafx.stage.Stage
import javafx.stage.Window
import net.ehvazend.mpu.FS_Handler
import net.ehvazend.mpu.FXML_Animation.Slider
import net.ehvazend.mpu.JSON_Handler
import net.ehvazend.mpu.data.JSON_DataModule
import net.ehvazend.mpu.data.JSON_DataPack
import java.io.File

open class FXML_Logic : FXML_Annotation() {
    protected object Initialization : FXML_Logic() {
        // Animations
        fun slide(vararg nameNode: Node) {
            for (value in nameNode) {
                Slider.addSlide(value)
            }
        }

        fun JSON(): ArrayList<JSON_DataPack> {
            return JSON_Handler.loaderPack()
        }
    }

    protected fun removeObject(node: Node) {
        Group().children.add(node)
    }

    // Post initialization
    protected fun loadEnded(dataPack: ArrayList<JSON_DataPack>) {
        // Update value
        fun packingDataModule() {
            dataPack.mapTo(dataModule) { ModuleAssociation(it.name, it.stateModules) }
        }

        Platform.runLater {
            packingDataModule()

            for ((name) in dataPack) comboBox_Root.items.add(name)

            // Also call event comboBox_ChangePack -> setStatePack
            comboBox_Root.value = dataPack[0].name

            comboBox_Root.isVisible = true
            comboBox_Root.isDisable = false

            removeObject(progressBar_Root)

            checkBox_Core.isVisible = true
            checkBox_ImprovedGraphics.isVisible = true
            checkBox_ImprovedGraphicsPlus.isVisible = true
        }
    }

    // State ---------------------------
    protected fun setStatePack(name: String) {
        fun update(dataModule: JSON_DataModule) {
            when (dataModule.name) {
                "core" -> {
                    setState(checkBox_Core, !dataModule.state)
                }
                "improvedGraphics" -> {
                    setState(checkBox_ImprovedGraphics, !dataModule.state)
                }
                "improvedGraphicsPlus" -> {
                    setState(checkBox_ImprovedGraphicsPlus, !dataModule.state)
                }
            }
        }

        dataModule
                .filter { it.name == name }
                .flatMap { it.listDataModule }
                .forEach { update(it) }
    }

    protected fun setState(node: CheckBox, disable: Boolean = node.isDisable, selected: Boolean = node.isSelected) {
        node.isDisable = disable
        node.isSelected = selected

        refreshState(node)
    }

    protected fun refreshState(node: CheckBox) {
        fun check(state: StateAssociation) {
            when {
                state.checkBox.isDisable -> {
                    state.titledPane.isDisable = state.checkBox.isDisable
                    if (state.checkBox.isDisable) titledPane_Core.isExpanded = false
                }
                else -> {
                    state.titledPane.isDisable = !state.checkBox.isSelected
                    if (!state.checkBox.isSelected) titledPane_Core.isExpanded = false
                }
            }
        }

        when (node) {
            bindingCore.checkBox -> check(bindingCore)
            bindingImprovedGraphics.checkBox -> check(bindingImprovedGraphics)
            bindingImprovedGraphicsPlus.checkBox -> check(bindingImprovedGraphicsPlus)
        }
    }
    // ---------------------------------

    // Directory path for install-------
    protected fun callChooseDirectory(initialDirectory: File, modalityWindow: Window): File? {

        val directoryChooser = DirectoryChooser()

        val stage = Stage().also {
            it.initOwner(modalityWindow)
            it.initModality(Modality.WINDOW_MODAL)
        }

        directoryChooser.initialDirectory = initialDirectory
        return directoryChooser.showDialog(stage.owner)

    }

    protected fun setInstallPath(path: File) {
        currentPath = path.let { if (it.toString().indexOf(NAME) != -1) it else File("$path\\$NAME") }
        refreshInstallPath()
    }

    protected fun setDefaultInstallPath(value: Boolean) {
        when (value) {
            true -> {
                bufferPath = currentPath
                setInstallPath(defaultPath)
            }
            false -> {
                setInstallPath(bufferPath)
            }
        }

        button_ChooseDirectory.isDisable = value
    }

    protected fun refreshInstallPath() {
        textField_InstallDirectory.text = currentPath.toString()
    }
    // ---------------------------------

    protected fun install(directory: File, catchingMode: Boolean = false, node: TextInputControl? = null): Boolean {
        fun concatenationPath(nameFile: String): File {
            return File("$directory\\" + nameFile)
        }

        fun separationPath(path: String): String {
            return path.replace(directory.toString(), "")
        }

        fun catchingValue(value: String, modeShort: Boolean = false) {
            if (catchingMode && node != null && !modeShort) node.appendText(value + "\n")
            if (catchingMode && node != null && modeShort) node.appendText(separationPath(value) + "\n")
        }

        catchingValue(FS_Handler.createDirectory(directory))
        catchingValue(FS_Handler.createFile(concatenationPath("config.json")), true)
        catchingValue(FS_Handler.copyCore(directory), true)

        return true
    }
}