package org.firstinspires.ftc.teamcode.subsystems.internal

import org.firstinspires.ftc.teamcode.component.Component

abstract class Subsystem<T: Subsystem<T>> {
    abstract val components: List<Component>

    open fun update(dt: Double = 0.0) { }

    open fun reset() {
        components.forEach { it.reset() }
    }

//    abstract fun write()
    open fun write() {
        components.forEach { it.write() }
    }

    open fun enable() { }
    open fun disable() { }

}