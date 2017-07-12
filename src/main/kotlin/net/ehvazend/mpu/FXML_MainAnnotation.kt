package net.ehvazend.mpu

import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.shape.Rectangle
import java.io.File

open class FXML_MainAnnotation {
    protected data class ModuleAssociation(val name: String, val listDataModule: ArrayList<JSON_DataModule>)
    protected data class StateAssociation(val checkBox: CheckBox, val titledPane: TitledPane)

    protected val NAME = "EhvazendMPU"
    protected val defaultPath = File("${System.getProperty("user.home")}\\AppData\\Roaming\\$NAME")
    protected var currentPath = defaultPath
    protected var bufferPath = currentPath

    protected val dataModule = ArrayList<ModuleAssociation>()

    protected lateinit var bindingCore: StateAssociation
    protected lateinit var bindingImprovedGraphics: StateAssociation
    protected lateinit var bindingImprovedGraphicsPlus: StateAssociation

    @FXML
    protected var panel_Root = Pane()

    @FXML
    protected var VBox_Home = VBox()

    @FXML
    protected var VBox_ModSelection = VBox()

    @FXML
    protected var VBox_Install = VBox()

    @FXML
    protected var image_Root = ImageView()

    @FXML
    protected var rectangle_Root = Rectangle()

    @FXML
    protected var textField_InstallDirectory = TextField()

    @FXML
    protected var button_ChooseDirectory = Button()

    @FXML
    protected var progressBar_Root = ProgressBar()

    @FXML
    protected var comboBox_Root = ComboBox<String>()

    // State
    @FXML
    protected var checkBox_Core = CheckBox()

    @FXML
    protected var checkBox_ImprovedGraphics = CheckBox()

    @FXML
    protected var checkBox_ImprovedGraphicsPlus = CheckBox()

    @FXML
    protected var titledPane_Core = TitledPane()

    @FXML
    protected var titledPane_ImprovedGraphics = TitledPane()

    @FXML
    protected var titledPane_ImprovedGraphicsPlus = TitledPane()
    //

    @FXML
    protected var checkBox_DefaultPath = CheckBox()
}