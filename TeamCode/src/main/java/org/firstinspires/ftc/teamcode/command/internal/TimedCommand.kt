package org.firstinspires.ftc.teamcode.command.internal

import com.qualcomm.robotcore.util.*
import org.firstinspires.ftc.teamcode.subsystems.*

//import org.firstinspires.ftc.teamcode.subsystems.Drivetrain

class TimedCommand(private val seconds: Double, private val command: Command = Command())
    //: Command(requirements = mutableSetOf(Drivetrain), name = { "TimedCommand" }) {
    : Command(requirements = command.requirements, name = { "TimedCommand" }) {
    constructor(seconds: Double, vararg requirements: Subsystem<*>, execute: (Double) -> Unit = { }): this(seconds, RunCommand(*requirements, function = execute))
    private val e = ElapsedTime()
    override fun initialize() {
        e.reset()
        command.initialize()
    }
    override fun execute(dt: Double) {
        command.execute(dt)
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

infix fun Command.withTimeout(seconds: Double) = TimedCommand(seconds, this)
