package net.ehvazend.mpu.install

import javafx.application.Platform
import javafx.geometry.Orientation
import javafx.scene.Group
import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.layout.VBox
import javafx.scene.text.Text
import javafx.stage.DirectoryChooser
import javafx.stage.Modality
import javafx.stage.Stage
import javafx.stage.Window
import net.ehvazend.mpu.FS_Handler
import net.ehvazend.mpu.FS_Repository
import net.ehvazend.mpu.FXML_Animation.Slider
import net.ehvazend.mpu.HTML_Updater
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

    protected fun isVision(node: Node, isVisible: Boolean = node.isVisible, isDisable: Boolean = node.isDisable) {
        node.also { it.isVisible = isVisible; it.isDisable = isDisable }
    }

    protected fun removeObject(node: Node) {
        Group().children.add(node)
    }

    // Post initialization
    protected fun loadEnded(dataPack: ArrayList<JSON_DataPack>) {
        // Update value
        fun packingDataModule() {
            dataPack.mapTo(this.dataPacks) { it }
        }

        Platform.runLater {
            packingDataModule()

            for ((_, name) in dataPack) {
                comboBox_Root.items.add(name)
            }

            // Also call event comboBox_ChangePack -> setState
            comboBox_Root.value = dataPack[0].name

            isVision(comboBox_Root, true, false)
            isVision(button_AddRepository, true, false)

            removeObject(progressBar_Root)

            isVision(checkBox_Core, true)
            isVision(checkBox_ImprovedGraphics, true)
            isVision(checkBox_ImprovedGraphicsPlus, true)
        }
    }

    // State ---------------------------
    protected fun setState(value: String) {
        fun update(pack: JSON_DataPack, dataModule: JSON_DataModule) {
            fun setTitledPane(arrayList: ArrayList<JSON_DataMod>, titledPane: TitledPane) {
                val VBox = VBox(6.0)

                VBox.children.add(Separator(Orientation.HORIZONTAL))

                for (mod in arrayList) {
                    VBox.children.add(CheckBox(mod.name).also {
                        it.isSelected = true; it.setOnAction {
                        mod.state = (it.source as CheckBox).isSelected
                    }
                    })
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
                                currentMods_Core = loaderMod(pack.repository, pack.hashName, module = dataModule).also { setTitledPane(it, titledPane_Core) }
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
                                currentMods_ImprovedGraphics = loaderMod(pack.repository, pack.hashName, module = dataModule).also { setTitledPane(it, titledPane_ImprovedGraphics) }
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
                                currentMods_ImprovedGraphicsPlus = loaderMod(pack.repository, pack.hashName, module = dataModule).also { setTitledPane(it, titledPane_ImprovedGraphicsPlus) }
                                refreshState(binding_ImprovedGraphicsPlus, DISABLE)
                            }
                        }
                    }

                    refreshState(binding_ImprovedGraphicsPlus)
                }
            }
        }

        for (pack: JSON_DataPack in dataPacks) when (pack.name) {
            value -> {
                currentDataPack = pack
                pack.stateModules.forEach { update(pack, it) }
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

        when (value.STATE) {
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

    protected fun installMode(mode: Boolean) = when (mode) {
        true -> {
            button_Past.isDisable = true
            button_Install.isDisable = true

            textArea_Install.isDisable = false
            textArea_Install.text = ""
        }
        false -> {
            button_Install.isDisable = true
            button_Install.isVisible = false

            button_Next.isDisable = false
            button_Next.isVisible = true
        }
    }

    protected fun install(directory: File, node: TextInputControl? = null) {
        fun concatenationPath(nameFile: String): File {
            return File("$directory\\" + nameFile)
        }

        fun separationPath(directory_: File, path: String): String {
            return path.replace(directory_.toString(), "")
        }

        fun textAreaHandler(value: String) {
            node?.appendText(value + "\n")
        }

        class ProgressBarHandler {
            private val installText = ArrayList<Text>().also { it.add(text_ValueOne); it.add(text_ValueSeparator); it.add(text_ValueTwo); }
            private var stepValue = 0.0


            fun start(maxValue: Int) {
                stepValue = 1.0 / maxValue
                progressBar_Install.progress = 0.0

                //text_ValueOne
                installText[0].also { it.text = "0" }

                // text_ValueTwo
                installText[2].text = maxValue.toString()

                installText.forEach { it.isVisible = true }
                progressBar_Install.isDisable = false
            }

            fun increment() {
                when {
                    installText[2].text.toInt() == 0 -> println("Text not initialized")
                    installText[0].text.toInt() == installText[2].text.toInt() -> println("Maximum value")
                    else -> {
                        installText[0].also { it.text = it.text.toInt().plus(1).toString() }
                        progressBar_Install.also { it.progress += stepValue }
                    }
                }
            }
        }

        fun install_MPU(progressBarHandler: ProgressBarHandler) {
            progressBarHandler.start(5)

            FS_Handler.createDirectory(directory).also { textAreaHandler(it); progressBarHandler.increment() }
            FS_Handler.createFile(concatenationPath("config.json")).also { textAreaHandler(separationPath(directory, it)); progressBarHandler.increment() }
            FS_Handler.copyCore(directory).also { textAreaHandler(separationPath(directory, it)); progressBarHandler.increment() }
        }

        fun install_Pack(progressBarHandler: ProgressBarHandler) {
            FS_Handler.createDirectory(File(directory, currentDataPack.hashName)).also { textAreaHandler(separationPath(directory, it)); progressBarHandler.increment() }
            FS_Handler.createDirectory(File(directory, File(currentDataPack.hashName, "mods").path)).also { textAreaHandler(separationPath(directory, it)); progressBarHandler.increment() }
        }

        fun install_Mods(progressBarHandler: ProgressBarHandler) {
            // Loading all mods
            ArrayList<JSON_DataMod>().also { it_ ->
                currentMods_Core.filter { it.state }.forEach { it_.add(it) }
                currentMods_ImprovedGraphics.filter { it.state }.forEach { it_.add(it) }
                currentMods_ImprovedGraphicsPlus.filter { it.state }.forEach { it_.add(it) }

            }.also { it_ -> progressBarHandler.also { it.start(it_.size) } }.forEach {
                HTML_Updater.checkMod("https://minecraft.curseforge.com/projects/${it.hashName}/files" + "?filter-game-version=${currentDataPack.hashVersion}").first().also {
                    FS_Handler.loadingFile(it.link + "/download", directory.path).also { textAreaHandler(separationPath(directory, it.path)); progressBarHandler.increment() }
                }
            }
        }

        ProgressBarHandler().also {
            install_MPU(it)
            install_Pack(it)
            install_Mods(it)
        }
    }
}