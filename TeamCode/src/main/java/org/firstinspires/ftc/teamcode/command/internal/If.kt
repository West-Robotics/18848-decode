package org.firstinspires.ftc.teamcode.command.internal


class If(
    private val supplier: () -> Boolean,
    private val trueCommand: Command = Command(),
    private val falseCommand: Command = Command(),
) : Command(name = { "if" }) {

    lateinit var command: Command

    infix fun Else(command: Command) = If(
        supplier,
        trueCommand,
        command,
    )

    override fun initialize() {
        command = if (supplier()) trueCommand else falseCommand
        this.requirements = command.requirements
        command.initialize()
    }

    override fun execute(dt: Double) {
        command.execute(dt)
    }

    override fun end(interrupted: Boolean) {
        command.end(interrupted)
    }

    override fun isFinished() = command.isFinished()
}
