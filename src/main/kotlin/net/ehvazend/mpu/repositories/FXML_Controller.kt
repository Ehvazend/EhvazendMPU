package net.ehvazend.mpu.repositories

import javafx.fxml.Initializable
import javafx.scene.control.ListView
import javafx.scene.effect.ColorAdjust
import javafx.scene.input.MouseEvent
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

    fun listView_RootCommit(actionEvent: MouseEvent) {
        (actionEvent.source as ListView<*>)
    }

    fun button_Edit() {
        listView_Root.edit(listView_Root.selectionModel.selectedIndex)
        listView_Root.prefHeight(100.0)
    }
}