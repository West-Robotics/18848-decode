package org.firstinspires.ftc.teamcode.opmodes


import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.command.internal.*
import org.firstinspires.ftc.teamcode.command.*
import org.firstinspires.ftc.teamcode.component.Component.Direction.*
import org.firstinspires.ftc.teamcode.subsystems.*
import org.firstinspires.ftc.teamcode.subsystems.Lifts.LiftPos.*
import org.firstinspires.ftc.teamcode.subsystems.Zone.*
import org.firstinspires.ftc.teamcode.util.Globals
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit.*
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.*
import kotlin.math.PI

@Autonomous(name = "test autp")
class TestAuto: CommandOpMode() {
    override fun onStart() {
        Drivetrain.resetToStartPos() // this makes it so that you can choose the start pos of robot

        Telemetry.addAll {
            "kicker pos" ids Kicker.sensor::position
        }

        fun launch_all() = (
            Launcher.run { speed = Zone.NEAR_FRONT.launcher_speed }
            with IntakeWheel.spin()
            with MidtakeWheel.spin()
        ) racesWith (
            (
                Lifts.raise(1)
                with Wait(2.5)
            )
            andThen (
                Lifts.raise(3)
                with Wait(0.5)
            )
            andThen (
                Kicker.pushOne()
                with Wait(1.5)
            )
            andThen (
                Kicker.pushOne()
                with Wait(0.5)
            )
            andThen (
                Lifts.raise(2)
                with Wait(1.0)
            )
            andThen Kicker.pushOne()
        ) withEnd {
            Lifts.resetLifts(Lifts.LiftPos.HOLD).command()
            Launcher.speed = 0.0
            MidtakeWheel.speed = 0.0
            IntakeWheel.speed = 0.0
        }

        // TODO: add blue coords
        // launch spot
        val launch = if (Globals.alliance == Globals.Alliance.RED) Pose2D(
            METER,
            0.8,
            1.0,
            DEGREES,
            -143.0,
        ) else Pose2D(
            METER,
            -0.85,
            0.90,
            DEGREES,
            -37.0,
        )

        // first spike
        val spike1 = if (Globals.alliance == Globals.Alliance.RED) Pose2D(
            METER,
            1.16,
            0.27,
            DEGREES,
            0.0
        ) else Pose2D(
            METER,
            -1.22,
            0.27,
            DEGREES,
            -120.0,
        )

        // second spike
        val spike2 = if (Globals.alliance == Globals.Alliance.RED) Pose2D(
            METER,
            1.17,
            0.27,
            DEGREES,
            0.0,
        ) else Pose2D(
            METER,
            -1.12,
            -0.244,
            DEGREES,
            -108.0,
        )

        (
            MoveToPointCommand(launch)
            andThen TurnCommand(PI/4)
        ).schedule() // dont forget to schedule it!
    }
}
