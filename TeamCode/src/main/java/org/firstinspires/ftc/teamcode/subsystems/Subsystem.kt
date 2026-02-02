package org.firstinspires.ftc.teamcode.subsystems

import org.firstinspires.ftc.teamcode.component.Component
import org.firstinspires.ftc.teamcode.command.internal.RunCommand
import org.firstinspires.ftc.teamcode.command.internal.InstantCommand

abstract class Subsystem<T: Subsystem<T>> {
    abstract val components: List<Component>

    open fun update(dt: Double = 0.0) {
        // components.forEach { it.update(dt) }
    }

    open fun reset() {
        components.forEach { it.reset() }
    }

//    abstract fun write()
    open fun write() {
        components.forEach { it.write() }
    }

    open fun enable() { }
    open fun disable() { }

    // the errors are worth it
    // i have hidden the errors hehe
    fun run(func: T.() -> Unit) =
        @Suppress("UNCHECKED_CAST")
        RunCommand(this) { (this as T).func() }

    fun runOnce(func: T.() -> Unit) =
        @Suppress("UNCHECKED_CAST")
        InstantCommand(this) { (this as T).func() }


}
