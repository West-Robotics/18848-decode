package org.firstinspires.ftc.teamcode.command.internal

class RaceCommand(vararg commandsInGroup: Command) : Command( name = { "RaceCommand" }) {
    var commands = unpack(commandsInGroup.asList())

    override fun initialize() {
        commands.forEach { it.initialize() }
    }

    override fun execute(dt: Double) {
        commands.forEach { it.execute(dt) }
    }

    override fun end(interrupted: Boolean) {
        commands.forEach { it.end(interrupted) }
    }

    override fun isFinished() = commands.count { it.isFinished() } > 0

    private fun unpack(commands: List<Command>): Array<out Command> {
        val output = arrayListOf<Command>()

        commands.forEach {
            if (it is RaceCommand) {
                output.addAll(
                    unpack(
                        it.commands.asList()
                    )
                )
            } else {
                output.add(it)
            }
        }

        return Array(
            size = output.size,
            init = { i -> output[i] }
        )
    }
}