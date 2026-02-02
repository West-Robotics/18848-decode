package org.firstinspires.ftc.teamcode.command.internal.group

import org.firstinspires.ftc.teamcode.command.internal.*

class AdvancingCommandGroup(vararg commandsInGroup: Command) : CommandGroup(*commandsInGroup) {
    private var index: Int = 0
        set(value) {
            field = value % commands.size
        }

    val current: Command get() = commands[index]

    private var advance = false

    private var scheduled = false

    fun advance() {
        advance = true
    }

    override fun schedule() {
        if (scheduled) advance()
        else super.schedule()
    }


    override fun initialize() {
        scheduled = true
        current.initialize()
    }

    override fun execute(dt: Double) {
        current.execute(dt)
        if (advance) {
            current.end(true)
            index++
            current.initialize()
            advance = false
        }
    }

    override fun end(interrupted: Boolean) {
        current.end(interrupted)
        scheduled = false
    }

    override fun isFinished() = current.isFinished() && !advance


}