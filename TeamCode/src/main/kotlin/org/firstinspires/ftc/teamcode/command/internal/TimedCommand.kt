package org.firstinspires.ftc.teamcode.command.internal

import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.subsystems.Subsystem

class TimedCommand(private val seconds: Double, private val command: Command = Command())
    : Command(requirements = command.requirements, name = { "TimedCommand" }) {
    constructor(seconds: Double, vararg requirements: Subsystem<*>, execute: () -> Unit = { }): this(seconds, RunCommand(*requirements, function = execute))
    private val e = ElapsedTime()
    override fun initialize() {
        e.reset()
        command.initialize()
    }
    override fun execute() {
        command.execute()
    }
    override fun end(interrupted: Boolean) {
        command.end(interrupted)
    }
    override fun isFinished() = (
        e.seconds() >= seconds
        || command.isFinished()
    )
}

fun Wait(seconds: Double) = TimedCommand(seconds = seconds)
