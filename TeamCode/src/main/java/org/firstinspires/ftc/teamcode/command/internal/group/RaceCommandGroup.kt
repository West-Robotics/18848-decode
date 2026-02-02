package org.firstinspires.ftc.teamcode.command.internal.group

import org.firstinspires.ftc.teamcode.command.internal.*

class RaceCommandGroup(vararg commandsInGroup: Command) : ParallelCommandGroup(*commandsInGroup) {
    override var name = { "RaceCommandGroup" }
    override fun isFinished() = commands.count { it.isFinished() } > 0
}