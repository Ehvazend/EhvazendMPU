package net.ehvazend.mpu

import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.shape.Rectangle

open class FXML_MainAnnotation {

    protected data class wrapperDataModule(val name: String, val listDataModule: ArrayList<JSON_DataModule>)

    protected val NAME = "EhvazendMPU"
    protected val dataModule = ArrayList<wrapperDataModule>()

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
    protected var textField_OutputDirectory = TextField()

    @FXML
    protected var button_ChooseDirectory = Button()

    @FXML
    protected var progressBar_Root = ProgressBar()

    @FXML
    protected var checkBox_Core = CheckBox()

    @FXML
    protected var checkBox_ImprovedGraphics = CheckBox()

    @FXML
    protected var checkBox_ImprovedGraphicsPlus = CheckBox()

    @FXML
    protected var comboBox_Root = ComboBox<String>()

    @FXML
    protected var titledPane_Core = TitledPane()

    @FXML
    protected var titledPane_ImprovedGraphics = TitledPane()

    @FXML
    protected var titledPane_ImprovedGraphicsPlus = TitledPane()
}