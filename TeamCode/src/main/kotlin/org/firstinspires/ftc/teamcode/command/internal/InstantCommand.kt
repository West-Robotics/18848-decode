package org.firstinspires.ftc.teamcode.command.internal

import org.firstinspires.ftc.teamcode.subsystems.internal.Subsystem

class InstantCommand(vararg requirements: Subsystem<*>, function: () -> Unit)
    : Command(execute = function, isFinished = { true }, requirements = requirements.toMutableSet())