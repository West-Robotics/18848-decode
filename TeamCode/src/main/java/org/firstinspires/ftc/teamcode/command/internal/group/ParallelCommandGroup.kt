package org.firstinspires.ftc.teamcode.command.internal.group

import org.firstinspires.ftc.teamcode.command.internal.*

open class ParallelCommandGroup(vararg commandsInGroup: Command) : CommandGroup(*commandsInGroup) {
    var finished = BooleanArray(commands.size) { false }

    override fun initialize() {
        finished = BooleanArray(commands.size) { false }
        commands.forEach { it.initialize() }
    }
    override fun execute(dt: Double) = commands.indices.forEach { i ->
        if (!finished[i]) commands[i].execute(dt)

        if (commands[i].isFinished()) {
            finished[i] = true
            commands[i].end(false)
        }
    }
    override fun end(interrupted: Boolean) = commands.indices.forEach { i -> if (!finished[i]) commands[i].end(interrupted) }
    override fun isFinished() = commands.all { it.isFinished() }

}