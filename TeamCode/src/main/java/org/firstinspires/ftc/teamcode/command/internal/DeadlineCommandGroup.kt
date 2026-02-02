package org.firstinspires.ftc.teamcode.command.internal

class DeadlineCommandGroup(val deadlineCommand: Command, vararg commandsInGroup: Command) : Command(name = { "DeadlineCommand" }) {
    var commands = commandsInGroup.asList()

    override fun initialize() {
        deadlineCommand.initialize()
        commands.forEach { it.initialize() }
    }

    override fun execute(dt: Double) {
        deadlineCommand.execute(dt)
        commands.forEach { it.execute(dt) }
    }

    override fun end(interrupted: Boolean) {
        deadlineCommand.end(interrupted)
        commands.forEach { it.end(interrupted) }
    }

    override fun isFinished() = deadlineCommand.isFinished()

}