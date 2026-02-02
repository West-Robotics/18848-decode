package org.firstinspires.ftc.teamcode.command.internal.group

import org.firstinspires.ftc.teamcode.command.internal.*

open class SequentialCommandGroup(vararg commandsInGroup: Command) : CommandGroup(*commandsInGroup) {
    override var name = { "SequentialCommandGroup" }
    var index: Int = 0
        internal set
    val current: Command
        get() = if (!isFinished()) commands[index]
                else Command()


    override fun initialize() {
        index = 0
        current.initialize()
    }

    override fun execute(dt: Double) {
        current.execute(dt)
        if (current.isFinished()) {
            current.end(false)
            index++
            if (index >= commands.size) return
            current.initialize()
        }
    }

    override fun end(interrupted: Boolean) {
        current.end(interrupted)
    }

    override fun isFinished() = index >= commands.size

}