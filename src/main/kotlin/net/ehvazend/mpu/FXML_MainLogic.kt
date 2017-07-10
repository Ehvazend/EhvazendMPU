package net.ehvazend.mpu

import javafx.application.Platform
import javafx.scene.Group
import javafx.scene.Node
import javafx.scene.control.CheckBox

open class FXML_MainLogic : FXML_MainAnnotation() {
    protected object Initialization : FXML_MainLogic() {
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
            dataPack.mapTo(dataModule) { ModuleAssociation(it.name, it.stateModules) }
        }

        Platform.runLater {
            packingDataModule()

            for ((name) in dataPack) comboBox_Root.items.add(name)

            // Also call event comboBox_ChangePack -> changeState
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
        // Priority - 0
        fun update(dataModule: JSON_DataModule) {
            when (dataModule.name) {
                "core" -> {
                    updateState(checkBox_Core, !dataModule.state)
                }
                "improvedGraphics" -> {
                    updateState(checkBox_ImprovedGraphics, !dataModule.state)
                }
                "improvedGraphicsPlus" -> {
                    updateState(checkBox_ImprovedGraphicsPlus, !dataModule.state)
                }
            }
        }

        dataModule
                .filter { it.name == name }
                .flatMap { it.listDataModule }
                .forEach { update(it) }
    }

    protected fun updateState(node: CheckBox, disable: Boolean = node.isDisable, selected: Boolean = node.isSelected) {
        node.isDisable = disable
        node.isSelected = selected

        refresh(node)
    }

    protected fun refresh(node: CheckBox) {
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

    protected fun removeObject(node: Node) {
        Group().children.add(node)
    }
}
