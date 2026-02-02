package org.firstinspires.ftc.teamcode.command.internal

import org.firstinspires.ftc.teamcode.subsystems.*

class DeferredCommand(
    vararg requirements: Subsystem<*>,
    private val supplier: () -> Command
) : Command(
    requirements = requirements.toMutableSet()
) {
    private var command: Command? = null
    override var name = {
        command?.toString() ?: "DeferredCommand"
    }

    override fun initialize() {
        command = supplier()
        command?.initialize()
    }

    override fun execute(dt: Double) {
        command?.execute(dt)
    }

    override fun end(interrupted: Boolean) {
        command?.end(interrupted)
    }

    override fun isFinished() = command?.isFinished() ?: false
}