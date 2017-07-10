package net.ehvazend.mpu

import javafx.application.Platform
import javafx.scene.Group
import javafx.scene.Node

open class FXML_MainLogic : FXML_MainAnnotation() {

    protected object Initialization: FXML_MainLogic() {
        // Animations
        fun slide(vararg nameNode: Node) {
            for (value in nameNode) {
                FXML_Animation.listSlides.add(value)
            }
        }

        fun effect(vararg nameNode: Node) {
            for (value in nameNode) {
                FXML_Animation.effectChange(value)
            }
        }

        fun JSON(): ArrayList<JSON_DataPack> {
            return JSON_Handler.packParser()
        }
    }

    protected fun loadEnded(dataPack: ArrayList<JSON_DataPack>) {
        // Update value
        fun packingDataModule() {
            dataPack.mapTo(dataModule) { wrapperDataModule(it.name, it.stateModules) }
        }

        Platform.runLater {
            packingDataModule()

            for ((name) in dataPack) comboBox_Root.items.add(name)
            comboBox_Root.value = dataPack[0].name

            comboBox_Root.isVisible = true
            comboBox_Root.isDisable = false

            removeObject(progressBar_Root)

            checkBox_Core.isVisible = true
            checkBox_ImprovedGraphics.isVisible = true
            checkBox_ImprovedGraphicsPlus.isVisible = true
        }
    }

    protected fun changeState(name: String) {
        fun update(dataModule: JSON_DataModule) {
            when(dataModule.name) {
                "core" -> { checkBox_Core.isDisable = !dataModule.state; titledPane_Core.isDisable = !dataModule.state }
                "improvedGraphics" -> { checkBox_ImprovedGraphics.isDisable = !dataModule.state; titledPane_ImprovedGraphics.isDisable = !dataModule.state }
                "improvedGraphicsPlus" -> { checkBox_ImprovedGraphicsPlus.isDisable = !dataModule.state; titledPane_ImprovedGraphicsPlus.isDisable = !dataModule.state }
            }
        }

        dataModule
                .filter { it.name == name }
                .flatMap { it.listDataModule }
                .forEach { update(it) }
    }

    protected fun removeObject(node: Node) {
        Group().children.add(node)
    }
}
