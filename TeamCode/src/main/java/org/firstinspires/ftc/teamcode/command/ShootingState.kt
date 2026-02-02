package org.firstinspires.ftc.teamcode.command

import org.firstinspires.ftc.teamcode.command.internal.group.*
import org.firstinspires.ftc.teamcode.subsystems.*
import org.firstinspires.ftc.teamcode.util.*
import kotlin.math.*

fun ShootingState() = (
        TurnCommand(Drivetrain.angleTo(Globals.alliance.goal) + PI)
        with Launcher.run {
            spinFromDistance(Drivetrain.distanceTo(Globals.alliance.goal))
        }
    )