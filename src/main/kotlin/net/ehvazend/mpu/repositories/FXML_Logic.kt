package net.ehvazend.mpu.repositories

import javafx.scene.control.cell.TextFieldListCell
import net.ehvazend.mpu.FS_Repository

open class FXML_Logic : FXML_Annotation() {
    protected fun loadRepositories() {
        listView_Root.items.addAll(FS_Repository.repositories())
    }

    protected fun editRepositories() {
        listView_Root.cellFactory = TextFieldListCell.forListView()
        listView_Root.setOnEditCommit { value ->
            listView_Root.items[value.index] = value.newValue
        }
    }

    // Button---------------------------
    protected fun add() {
        listView_Root.items.add("")

        (listView_Root.items.size - 1).also {
            listView_Root.selectionModel.select(it)
            edit(it)
        }

    }

    protected fun edit() {
        listView_Root.also {
            it.edit(listView_Root.selectionModel.selectedIndex)
            it.setOnEditCancel { FS_Repository.check(listView_Root.items[listView_Root.selectionModel.selectedIndex]) }
        }

        listView_Root.edit(listView_Root.selectionModel.selectedIndex)
        listView_Root.setOnEditCancel { FS_Repository.check(listView_Root.items[listView_Root.items.size - 1]) }
    }

    protected fun edit(index: Int) {
        listView_Root.edit(index)
    }

    protected fun delete() {
        listView_Root.items.removeAt(listView_Root.selectionModel.selectedIndex)
    }

    protected fun delete(index: Int) {
        listView_Root.items.removeAt(index)
    }
    // ---------------------------------

    protected fun checkVoid() {
        listView_Root.items
                .filter { it == "" }
                .forEach { delete(listView_Root.items.indexOf(it)) }
    }
}