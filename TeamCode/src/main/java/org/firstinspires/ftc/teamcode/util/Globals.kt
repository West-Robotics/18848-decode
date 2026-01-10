package org.firstinspires.ftc.teamcode.util

import org.firstinspires.ftc.robotcore.external.navigation.Pose2D
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit.*
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.*

object Globals {


    var alliance by Selector("alliance", Alliance.RED, Alliance.BLUE)
    var start_pos by Selector("start pos", StartPos.BACKZONE, StartPos.GOAL)
    var randomization = Randomization.UNKNOWN


    enum class StartPos(val red_pos: Pose2D, val blue_pos: Pose2D) {
        GOAL(
            red_pos = Pose2D(
                INCH,
                 0.0,
                 0.0,
                DEGREES,
                0.0,
            ),
            blue_pos = Pose2D(
                INCH,
                 0.0,
                 0.0,
                DEGREES,
                0.0,
            )
        ),
        BACKZONE(
            red_pos = Pose2D(
                INCH,
                 0.0,
                 0.0,
                DEGREES,
                0.0,
            ),
            blue_pos = Pose2D(
                INCH,
                 0.0,
                 0.0,
                DEGREES,
                0.0,
            )
        );

        fun get() = when (Globals.alliance) {
            Alliance.RED -> this.red_pos
            Alliance.BLUE -> this.blue_pos
        }

    }

    enum class Alliance(val goal: Pose2D) {
        RED(
            Pose2D(
                INCH,
                -60.0,
                 60.0,
                DEGREES,
                0.0
            )
        ),
        BLUE(
            Pose2D(
                INCH,
                 60.0,
                -60.0,
                DEGREES,
                0.0
            )
        ),
    }

    enum class Randomization {
        PPG, PGP, GPP, UNKNOWN
    }

    enum class BallColor {
        PURPLE, GREEN
    }
}
