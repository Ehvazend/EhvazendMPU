package net.ehvazend.mpu.repositories

import javafx.animation.Timeline
import javafx.fxml.FXML
import javafx.scene.control.ListView
import javafx.scene.shape.Rectangle

open class FXML_Annotation {
    protected lateinit var RootEffect: Timeline

    @FXML
    protected var rectangle_Root = Rectangle()

    @FXML
    protected var listView_Root = ListView<String>()
}