package org.firstinspires.ftc.teamcode.command.internal.group

import org.firstinspires.ftc.teamcode.command.internal.*

abstract class CommandGroup(vararg commandsInGroup: Command) : Command(name = { "CommandGroup" }) {
    protected var commands = mutableListOf<Command>()

    init {
        addCommands(*commandsInGroup)
    }

    open fun addCommand(command: Command) {
        commands.add(command)
        command.requirements.forEach { addRequirement(it) }
    }

    open fun addCommands(vararg commands: Command) {
        commands.forEach(::addCommand)
    }

    override fun initialize() {
        commands.forEach { it.initialize() }
    }

    override fun execute(dt: Double) {
        commands.forEach { it.execute(dt) }
    }

    override fun end(interrupted: Boolean) {
        commands.forEach { it.end(interrupted) }
    }


}