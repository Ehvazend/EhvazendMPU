package net.ehvazend.mpu

import javafx.animation.Interpolator
import javafx.animation.TranslateTransition
import javafx.scene.Node
import javafx.util.Duration

object FXMLAnimation {
    var nodeList: ArrayList<Node> = ArrayList()
    var activeNodeNumber: Int = 0

    fun animationNode(direction: Direction) {
        val newActiveSlideNumber = direction.codeNumber

        // Exception block without crash
        if (nodeList.isEmpty()) {
            System.err.println("Node List cannot be empty"); return
        }
        if (newActiveSlideNumber > 1 || newActiveSlideNumber < -1) {
            System.err.println("Active Node Number can only -1 or 1"); return
        }
        if ((activeNodeNumber + newActiveSlideNumber) == -1) {
            System.err.println("Active Node Number cannot be -1"); return
        }
        if ((activeNodeNumber + newActiveSlideNumber) == activeNodeNumber) {
            System.err.println("Active Node Number cannot be unchanged"); return
        }
        if ((activeNodeNumber + newActiveSlideNumber) >= (nodeList.size)) {
            System.err.println("It is over"); return
        }

        animationPlay(nodeList[activeNodeNumber], Duration.seconds(0.75), globalCoordinateToLocal(nodeList[activeNodeNumber], direction.coordinate))
        animationPlay(nodeList[activeNodeNumber + newActiveSlideNumber], Duration.seconds(0.75), globalCoordinateToLocal(nodeList[activeNodeNumber + newActiveSlideNumber], 0.0))

        activeNodeNumber += newActiveSlideNumber
        // Finish
    }

    // Конвертация глобальных систем координат в локальные
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
}