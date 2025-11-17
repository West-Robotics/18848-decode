package org.firstinspires.ftc.teamcode.component

abstract class Component {


    abstract fun update(dt: Double)
    abstract fun reset()
    abstract fun write()

    enum class Direction(val dir: Int) {
        FORWARD(1),
        REVERSE(-1)
    }
}