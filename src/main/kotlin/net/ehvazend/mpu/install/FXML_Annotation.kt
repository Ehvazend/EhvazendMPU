package net.ehvazend.mpu.install

import javafx.animation.Timeline
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.shape.Rectangle
import javafx.scene.text.Text
import net.ehvazend.mpu.data.JSON_DataMod
import net.ehvazend.mpu.data.JSON_DataPack
import java.io.File

open class FXML_Annotation {
    protected val NAME = "EhvazendMPU"

    protected lateinit var RootEffect: Timeline

    protected val defaultPath = File("${System.getProperty("user.home")}\\AppData\\Roaming")
    protected lateinit var currentPath: File
    protected lateinit var bufferPath: File

    protected val dataPacks = ArrayList<JSON_DataPack>()
    protected lateinit var currentDataPack: JSON_DataPack

    protected enum class State_TitledPane { DISABLE, LOADING, ENABLE }
    protected data class StateAssociation(val checkBox: CheckBox, val titledPane: TitledPane, var STATE: State_TitledPane = State_TitledPane.DISABLE)

    protected var currentMods_Core = ArrayList<JSON_DataMod>()
    protected var currentMods_ImprovedGraphics = ArrayList<JSON_DataMod>()
    protected var currentMods_ImprovedGraphicsPlus = ArrayList<JSON_DataMod>()

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

    @FXML
    protected var button_Past = Button()

    @FXML
    protected var button_Next = Button()

    @FXML
    protected var button_Install = Button()

    @FXML
    protected var textArea_Install = TextArea()

    @FXML
    protected var progressBar_Install = ProgressBar()

    @FXML
    protected var text_ValueOne = Text()

    @FXML
    protected var text_ValueSeparator = Text()

    @FXML
    protected var text_ValueTwo = Text()
}