package org.firstinspires.ftc.teamcode.command

import org.firstinspires.ftc.teamcode.command.internal.*
import org.firstinspires.ftc.teamcode.subsystems.*
import org.firstinspires.ftc.teamcode.subsystems.Zone.*

fun LaunchPreloads(zone: Zone) = (
    Launcher.spin(zone)
        racesWith (
            Kicker.pushOne() andThen
            Wait(1.0) andThen
            Kicker.pushOne() andThen
            Wait(1.0) andThen
            Kicker.pushOne() andThen
            Wait(1.0) andThen
            (Kicker.gyrate(-0.5) withTimeout 0.5) andThen
            (MidtakeWheel.spin() withTimeout 2.0) andThen
            (Kicker.gyrate(0.5) withTimeout 0.6)
        )
)
