package net.ehvazend.mpu.repositories

import javafx.fxml.Initializable
import javafx.scene.effect.ColorAdjust
import net.ehvazend.mpu.FXML_Animation
import java.net.URL
import java.util.*
import kotlin.concurrent.thread

class FXML_Controller : FXML_Logic(), Initializable {
    override fun initialize(location: URL, resources: ResourceBundle) {
        RootEffect = FXML_Animation.Timeline((rectangle_Root.effect as ColorAdjust).hueProperty(), value = 1.0, duration = 20.0)

        thread {
            loadRepositories()
            editRepositories()
            RootEffect.play()
        }
    }

    fun button_Add() {
        add()
    }

    fun button_Edit() {
        edit()
    }

    fun button_Delete() {
        delete()
    }

    fun button_Save() {

    }

    fun listView_Cancel() {
        checkVoid()
    }
}