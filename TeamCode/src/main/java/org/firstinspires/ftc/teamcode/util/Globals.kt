package org.firstinspires.ftc.teamcode.util

import org.firstinspires.ftc.robotcore.external.navigation.Pose2D
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit

object Globals {


    // var alliance by Selector("alliance", RED, BLUE)
    var alliance: Alliance = Alliance.RED





    enum class Alliance(val goal: Pose2D) {
        RED(
            Pose2D(
                DistanceUnit.INCH,
                -60.0,
                 60.0,
                AngleUnit.DEGREES,
                0.0
            )
        ),
        BLUE(
            Pose2D(
                DistanceUnit.INCH,
                 60.0,
                -60.0,
                AngleUnit.DEGREES,
                0.0
            )
        ),
        UNKNOWN(
            Pose2D(
                DistanceUnit.INCH,
                0.0,
                0.0,
                AngleUnit.DEGREES,
                0.0
            )
        )
    }

    enum class Randomization {
        PPG, PGP, GPP, UNKOWN
    }

    enum class BallColor {
        PURPLE, GREEN, UNKNOWN
    }
}
