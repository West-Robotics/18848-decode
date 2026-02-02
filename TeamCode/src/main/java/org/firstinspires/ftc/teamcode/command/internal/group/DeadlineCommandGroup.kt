package org.firstinspires.ftc.teamcode.command.internal.group

import org.firstinspires.ftc.teamcode.command.internal.*

class DeadlineCommandGroup(val deadlineCommand: Command, vararg commandsInGroup: Command)
    : ParallelCommandGroup(deadlineCommand, *commandsInGroup) {
    override var name = { "DeadlineCommandGroup" }
    override fun isFinished() = deadlineCommand.isFinished()

}