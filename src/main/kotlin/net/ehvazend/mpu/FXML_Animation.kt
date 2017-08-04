package net.ehvazend.mpu

import javafx.animation.*
import javafx.beans.property.Property
import javafx.scene.Node
import javafx.util.Duration

object FXML_Animation {

    object Slider {
        var process = false

        private var activeSlideNumber = 0
        private val listSlides = ArrayList<Node>()

        fun addSlide(slide: Node) {
            listSlides.add(slide)
        }

        enum class Direction(val coordinate: Double, val codeNumber: Int) {
            LEFT(600.0, -1), RIGHT(-600.0, 1)
        }

        fun step(direction: Direction) {
            val newActiveSlideNumber = direction.codeNumber

            // Exception block without crash
            when {
                process -> println("Reloading")
                listSlides.isEmpty() -> println("Node List cannot be empty")
                (activeSlideNumber + newActiveSlideNumber) == -1 -> println("Active Node Number cannot be -1")
                (activeSlideNumber + newActiveSlideNumber) == activeSlideNumber -> println("Active Node Number cannot be unchanged")
                (activeSlideNumber + newActiveSlideNumber) >= (listSlides.size) -> println("It is over")
                else -> {
                    animationPlay(
                            node = listSlides[activeSlideNumber],
                            duration = Duration.seconds(0.75),
                            coordinate = globalCoordinateToLocal(listSlides[activeSlideNumber], direction.coordinate)
                    )
                    animationPlay(
                            node = listSlides[activeSlideNumber + newActiveSlideNumber],
                            duration = Duration.seconds(0.75),
                            coordinate = globalCoordinateToLocal(listSlides[activeSlideNumber + newActiveSlideNumber], 0.0)
                    )

                    activeSlideNumber += newActiveSlideNumber
                }
            }
        }

        // Conversion of global coordinate systems to local
        private fun globalCoordinateToLocal(node: Node, globalCoordinate: Double): Double {
            return globalCoordinate - node.layoutX
        }

        private fun animationPlay(node: Node, duration: Duration, coordinate: Double) {
            val transition: TranslateTransition = TranslateTransition()
            val DY: Double = 0.2
            val DX: Double = 1.0

            transition.node = node
            transition.duration = duration
            transition.interpolator = Interpolator.SPLINE(DX, DY, DY, DX)
            transition.toX = coordinate
            transition.play().also { process = true }
            transition.setOnFinished { process = false }
        }
    }

    fun Timeline(nodeProperty: Property<Number>, value: Number, duration: Double): Timeline {
        val keyValue = KeyValue(nodeProperty, value)
        val keyFrame = KeyFrame(Duration.seconds(duration), keyValue)
        val timeline = Timeline(keyFrame)
        timeline.cycleCount = Timeline.INDEFINITE
        return timeline
    }
}