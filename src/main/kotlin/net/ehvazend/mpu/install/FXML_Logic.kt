package net.ehvazend.mpu.install

import javafx.application.Platform
import javafx.geometry.Orientation
import javafx.scene.Group
import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.layout.VBox
import javafx.stage.DirectoryChooser
import javafx.stage.Modality
import javafx.stage.Stage
import javafx.stage.Window
import net.ehvazend.mpu.FS_Handler
import net.ehvazend.mpu.FS_Repository
import net.ehvazend.mpu.FXML_Animation.Slider
import net.ehvazend.mpu.JSON_Handler
import net.ehvazend.mpu.JSON_Handler.loaderMod
import net.ehvazend.mpu.data.JSON_DataMod
import net.ehvazend.mpu.data.JSON_DataModule
import net.ehvazend.mpu.data.JSON_DataPack
import net.ehvazend.mpu.install.FXML_Annotation.State_TitledPane.*
import java.io.File
import kotlin.concurrent.thread

open class FXML_Logic : FXML_Annotation() {
    protected object Initialization : FXML_Logic() {
        // Animations
        fun slide(vararg nameNode: Node) {
            for (value in nameNode) {
                Slider.addSlide(value)
            }
        }

        fun JSON(): ArrayList<JSON_DataPack> = ArrayList<JSON_DataPack>().let {
            for (value in FS_Repository.repositories()) {
                it.addAll(JSON_Handler.loaderPack(value, "Packs.json"))
            }

            return it
        }
    }

    protected fun removeObject(node: Node) {
        Group().children.add(node)
    }

    // Post initialization
    protected fun loadEnded(dataPack: ArrayList<JSON_DataPack>) {
        // Update value
        fun packingDataModule() {
            dataPack.mapTo(dataModule) { ModuleAssociation(it.repository, it.name, it.hash, it.stateModules) }
        }

        Platform.runLater {
            packingDataModule()

            for ((_, name) in dataPack) {
                comboBox_Root.items.add(name)
            }

            // Also call event comboBox_ChangePack -> setState
            comboBox_Root.value = dataPack[0].name

            comboBox_Root.isVisible = true
            comboBox_Root.isDisable = false

            button_AddRepository.isVisible = true
            button_AddRepository.isDisable = false

            removeObject(progressBar_Root)

            checkBox_Core.isVisible = true
            checkBox_ImprovedGraphics.isVisible = true
            checkBox_ImprovedGraphicsPlus.isVisible = true
        }
    }

    // State ---------------------------
    protected fun setState(value: String) {
        fun update(repository: String, hash: String, dataModule: JSON_DataModule) {
            fun setTitledPane(arrayList: ArrayList<JSON_DataMod>, titledPane: TitledPane) {
                val VBox = VBox(6.0)

                VBox.children.add(Separator(Orientation.HORIZONTAL))

                for ((name) in arrayList) {
                    VBox.children.add(CheckBox(name).also { it.isSelected = true })
                }

                VBox.children.add(Separator(Orientation.HORIZONTAL))

                titledPane.content = VBox
            }


            when (dataModule.name) {
                "core" -> {
                    binding_Core.checkBox.isDisable = !dataModule.state
                    when {
                        dataModule.state -> thread {
                            Platform.runLater {
                                refreshState(binding_Core, LOADING)
                                loaderMod(repository, hash, module = dataModule).also { setTitledPane(it, titledPane_Core) }
                                refreshState(binding_Core, DISABLE)
                            }
                        }
                    }

                    refreshState(binding_Core)
                }

                "improvedGraphics" -> {
                    binding_ImprovedGraphics.checkBox.isDisable = !dataModule.state
                    when {
                        dataModule.state -> thread {
                            Platform.runLater {
                                refreshState(binding_ImprovedGraphics, LOADING)
                                loaderMod(repository, hash, module = dataModule).also { setTitledPane(it, titledPane_ImprovedGraphics) }
                                refreshState(binding_ImprovedGraphics, DISABLE)
                            }
                        }
                    }

                    refreshState(binding_ImprovedGraphics)
                }

                "improvedGraphicsPlus" -> {
                    binding_ImprovedGraphicsPlus.checkBox.isDisable = !dataModule.state
                    when {
                        dataModule.state -> thread {
                            Platform.runLater {
                                refreshState(binding_ImprovedGraphicsPlus, LOADING)
                                loaderMod(repository, hash, module = dataModule).also { setTitledPane(it, titledPane_ImprovedGraphicsPlus) }
                                refreshState(binding_ImprovedGraphicsPlus, DISABLE)
                            }
                        }
                    }

                    refreshState(binding_ImprovedGraphicsPlus)
                }
            }
        }

        for ((repository, name, hash, listDataModule) in dataModule) {
            when (name) {
                value -> listDataModule.forEach { update(repository, hash, it) }
            }
        }
    }

    protected fun refreshState(value: StateAssociation, STATE: State_TitledPane? = null) {
        when {
            STATE != null -> value.STATE = STATE
        }

        when {
            value.STATE == LOADING -> Unit
            value.checkBox.isDisable -> value.STATE = DISABLE
            value.checkBox.isSelected -> value.STATE = ENABLE
            else -> value.STATE = DISABLE
        }

        when(value.STATE) {
            DISABLE -> {
                value.titledPane.isDisable = true
                value.titledPane.graphic = null
                value.titledPane.isExpanded = false
            }
            LOADING -> {
                value.titledPane.isDisable = true
                value.titledPane.graphic = ProgressBar()
                value.titledPane.isExpanded = false
            }
            ENABLE -> {
                value.titledPane.isDisable = false
                value.titledPane.graphic = null
            }
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