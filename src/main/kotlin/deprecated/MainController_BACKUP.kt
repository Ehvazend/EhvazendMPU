package deprecated

import javafx.fxml.Initializable
import javafx.stage.DirectoryChooser
import javafx.stage.Stage
import net.ehvazend.mpu.*
import net.ehvazend.mpu.FXML_Animation.slider
import net.ehvazend.mpu.Install.installMPU
import java.io.File
import java.net.URL
import java.util.ResourceBundle
import kotlin.concurrent.thread

class MainController_BACKUP : FXMLAnnotation_BACKUP(), Initializable {
    override fun initialize(location: URL, resources: ResourceBundle) {
        updateTextFileInput(defaultPath.path)

        FXML_Animation.listSlides.add(paneSlideOne)
        FXML_Animation.listSlides.add(paneSlideTwo)
        FXML_Animation.listSlides.add(paneSlideThree)

        val coreJSON: ArrayList<JSON_DataPack> = JSON_Handler.packParser()
        val dataJSON: JSON_DataMPU = JSON_Handler.dataMPU()

        for (value in coreJSON) {
            println(value)
        }

        println(dataJSON)
    }

    // Создание переменных и констант
    // Creating variables and constants
    private val NAME = "EhvazendMPU"

    private var defaultPath: File = File("${System.getProperty("user.home")}\\AppData\\Roaming\\$NAME")
    private var currentPath: File? = defaultPath
    private var pastPath: File = defaultPath

    fun updateTextFileInput() {
        textFileInput.text = currentPath?.path
    }

    fun updateTextFileInput(newText: String?) {
        textFileInput.text = newText
    }

    // Выбор директории
    // Функционал - Функция changeCurrentDirectory: Принимает новый путь и заменяет им старый. Так-же добавляет в путь константу NAME или не делает ничего если она уже есть
    // Select directory
    // Functionality - The function changeCurrentDirectory: Accepts a new path and replaces the old one. It also adds a NAME constant to the path or does nothing if it already exists
    fun chooseDirectory() {
        val directoryChooser: DirectoryChooser = DirectoryChooser()

        fun changeCurrentDirectory(newCurrentDirectory: File?, currentDirectory: File?, name: String): File? {
            if (newCurrentDirectory == null) return currentDirectory
            if (newCurrentDirectory.toString().indexOf(name) != -1) return newCurrentDirectory else return File("$newCurrentDirectory\\$name")
        }

        // Открывает окно с пути "currentPath - NAME"
        // Opens a window from the path "currentPath - NAME"
        directoryChooser.initialDirectory = File(currentPath.toString().replace(NAME, ""))
        currentPath = changeCurrentDirectory(directoryChooser.showDialog(Stage()), currentPath, NAME)

        updateTextFileInput()
    }

    // При нажатии заменяет поле на стандартный путь и выключает кнопку выбора пути. При отжатии восстонавливает путь и делает кнопку активной
    // When pressed, replaces the field with the standard path and turns off the path selection button. In the opposite case, it restores the path and makes the button active
    fun changeCheckBoxDefaultPath() {
        when (checkBoxDefaultPath.isSelected) {
            true -> {
                pastPath = currentPath!!
                currentPath = defaultPath
                updateTextFileInput(currentPath?.path)
                buttonChooseDirectory.isDisable = true
            }
            false -> {
                currentPath = pastPath
                updateTextFileInput(currentPath?.path)
                buttonChooseDirectory.isDisable = false
            }
        }
    }

    fun nextStep() {
        slider(Direction.RIGHT)
    }

    fun pastStep() {
        slider(Direction.LEFT)
    }

    fun install() {
        install.isDisable = true
        textInstall.isDisable = false
        installProgressBar.isDisable = false

        thread(name = "install") {
            installMPU(currentPath!!, true, textInstall)
        }
    }
}