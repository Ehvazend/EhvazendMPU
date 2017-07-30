package net.ehvazend.mpu.install

import javafx.animation.Timeline
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.shape.Rectangle
import net.ehvazend.mpu.data.JSON_DataModule
import java.io.File

open class FXML_Annotation {
    protected enum class State_TitledPane { DISABLE, LOADING, ENABLE }

    protected data class ModuleAssociation(val repository: String, val name: String, val hash: String, val listDataModule: ArrayList<JSON_DataModule>)
    protected data class StateAssociation(val checkBox: CheckBox, val titledPane: TitledPane, var STATE: State_TitledPane = State_TitledPane.DISABLE)

    protected val dataModule = ArrayList<ModuleAssociation>()

    protected val NAME = "EhvazendMPU"

    protected val defaultPath = File("${System.getProperty("user.home")}\\AppData\\Roaming")
    protected lateinit var currentPath: File
    protected lateinit var bufferPath: File

    protected lateinit var RootEffect: Timeline

    protected lateinit var binding_Core: StateAssociation
    protected lateinit var binding_ImprovedGraphics: StateAssociation
    protected lateinit var binding_ImprovedGraphicsPlus: StateAssociation

    @FXML
    protected var pane_Root = Pane()

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
    protected var button_AddRepository = Button()

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