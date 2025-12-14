package org.firstinspires.ftc.teamcode.command

import org.firstinspires.ftc.teamcode.subsystems.*

fun LaunchBallCommand(zone: Zone) = (
    Launcher.spin(zone) with 
    Kicker.runToPos(Kicker.KickerPos.HIGH)
) andThen Kicker.runToPos(Kicker.KickerPos.LOW)
