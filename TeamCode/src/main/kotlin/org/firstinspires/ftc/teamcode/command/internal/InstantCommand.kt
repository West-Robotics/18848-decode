package org.firstinspires.ftc.teamcode.command.internal

import org.firstinspires.ftc.teamcode.subsystems.Subsystem

class InstantCommand(vararg requirements: Subsystem<*>, var command: () -> Unit) : 
Command(initialize = command, isFinished = { true }, requirements = requirements.toMutableSet(), name = { "InstantCommand" }) {
    // final override fun isFinished() = true
}
