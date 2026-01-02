package org.firstinspires.ftc.teamcode.command.internal

import org.firstinspires.ftc.teamcode.subsystems.Subsystem

class RunCommand(vararg requirements: Subsystem<*>, function: (Double) -> Unit)
    : Command(execute = function, isFinished = { false }, requirements = requirements.toMutableSet(), name = { "RunCommand" })
