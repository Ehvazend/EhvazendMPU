package net.ehvazend.mpu

import javafx.animation.*
import javafx.scene.Node
import javafx.scene.effect.ColorAdjust
import javafx.util.Duration

object FXML_Animation {
    var listSlides = ArrayList<Node>()
    var activeNodeNumber = 0

    fun slider(direction: Direction) {
        val newActiveSlideNumber = direction.codeNumber

        // Exception block without crash
        when {
            listSlides.isEmpty() -> println("Node List cannot be empty")
            (activeNodeNumber + newActiveSlideNumber) == -1 -> println("Active Node Number cannot be -1")
            (activeNodeNumber + newActiveSlideNumber) == activeNodeNumber -> println("Active Node Number cannot be unchanged")
            (activeNodeNumber + newActiveSlideNumber) >= (listSlides.size) -> println("It is over")
            else -> {
                animationPlay(listSlides[activeNodeNumber], Duration.seconds(0.75), globalCoordinateToLocal(listSlides[activeNodeNumber], direction.coordinate))
                animationPlay(listSlides[activeNodeNumber + newActiveSlideNumber], Duration.seconds(0.75), globalCoordinateToLocal(listSlides[activeNodeNumber + newActiveSlideNumber], 0.0))

                activeNodeNumber += newActiveSlideNumber
            }
        }
    }

    // Conversion of global coordinate systems to local
    private fun globalCoordinateToLocal(node: Node, globalCoordinate: Double): Double {
        return globalCoordinate - node.layoutX
    }

    // Animation block
    private fun animationPlay(node: Node, duration: Duration, coordinate: Double) {
        val transition: TranslateTransition = TranslateTransition()
        val DY: Double = 0.2
        val DX: Double = 1.0

        transition.node = node
        transition.duration = duration
        transition.interpolator = Interpolator.SPLINE(DX, DY, DY, DX)
        transition.toX = coordinate
        transition.play()
    }

    fun effectChange(node: Node) {
        val effect = ColorAdjust(-1.0, 0.0, 0.0, 0.0)
        node.effect = effect
        val keyValue = KeyValue(effect.hueProperty(), 1.0)
        val keyFrame = KeyFrame(Duration.seconds(20.0), keyValue)
        val timeline = Timeline(keyFrame)
        timeline.cycleCount = Timeline.INDEFINITE
        timeline.play()
    }
}