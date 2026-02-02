package org.firstinspires.ftc.teamcode.command.internal.group

import org.firstinspires.ftc.teamcode.command.internal.*

class AdvancingCommandGroup(vararg commandsInGroup: Command) : CommandGroup(*commandsInGroup) {
    private var index: Int = 0
        get() = field
        set(value) {
            field = value % commands.size
        }

    val current: Command get() = commands[index]

    private var manualAdvance: Int = 0

    fun advance() {
        manualAdvance += 1
    }

    override fun initialize() {
        current.initialize()
    }

    override fun execute(dt: Double) {
        current.execute(dt)
    }

    override fun end(interrupted: Boolean) {
        current.end(interrupted)
        if (manualAdvance >= 1) {
            index += manualAdvance
            manualAdvance = 0
            this.schedule()
        } else {
            index++
        }
    }

    override fun isFinished() = current.isFinished() || manualAdvance >= 1


}