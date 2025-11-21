package org.firstinspires.ftc.teamcode.command.internal

open class CommandGroup(vararg commandsInGroup: Command) : Command(name = { "CommandGroup" }) {
    var commands = unpack(commandsInGroup.asList())

    override var requirements = commands.flatMap {
        it.requirements
    }.toMutableSet()

    private var index = 0
    private val current: Command
        get() = if (!isFinished()) commands[index]
                else Command()

    override fun initialize() {
        commands[0].initialize()
        index = 0
    }
    override fun execute() {
        current.execute()
        if (current.isFinished()) {
            current.end(false)

            index++
            if (index < commands.size) {
                current.initialize()
            }
        }
    }
    override fun end(interrupted: Boolean) {
        current.end(interrupted)
    }
    override fun isFinished() = index >= commands.size

    private fun unpack(commands: List<Command>): Array<out Command> {
        val output = arrayListOf<Command>()

        commands.forEach {
            if (it is CommandGroup) {
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