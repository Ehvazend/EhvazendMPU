package net.ehvazend.mpu.repositories

import javafx.scene.control.cell.TextFieldListCell
import net.ehvazend.mpu.Repository

open class FXML_Logic : FXML_Annotation() {
    fun loadRepositories() {
        listView_Root.items.addAll(Repository.repositories())
    }

    fun editRepositories() {
        listView_Root.cellFactory = TextFieldListCell.forListView()
        listView_Root.setOnEditCommit { value ->
            listView_Root.items[value.index] = value.newValue
        }
    }
}