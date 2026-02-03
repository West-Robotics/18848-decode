package org.firstinspires.ftc.teamcode.opmodes

import com.qualcomm.robotcore.eventloop.opmode.*
import org.firstinspires.ftc.teamcode.command.*
import org.firstinspires.ftc.teamcode.command.internal.*
import org.firstinspires.ftc.teamcode.command.internal.group.*
import org.firstinspires.ftc.teamcode.subsystems.*
import org.firstinspires.ftc.teamcode.subsystems.Launcher.spinFromDistance
import org.firstinspires.ftc.teamcode.util.*
import kotlin.math.*

@TeleOp(name = "testing!")
class TestTeleop : CommandOpMode() {
    override fun onStart() {

        val drive = TeleOpDrive(
//            { driver.left_stick.x.sq },
            { -driver.left_stick.y.sq },
            { driver.right_stick.x.sq },
            0.9
        )

        Telemetry.show_commands = true
        Telemetry.addAll {
            "position" ids Drivetrain::posString
            "kicker thing" ids Kicker.sensor::position
        }

        fun launch_all() = TurnCommand(Drivetrain.angleTo(Globals.alliance.goal) + PI) then (
                (
                    Launcher.run { spinFromDistance(Drivetrain.distanceTo(Globals.alliance.goal)) }
                        with IntakeWheel.spin()
                        with MidtakeWheel.spin()
                ) races (
                    (
                    Lifts.raise(1)
                            with Wait(2.5)
                    )
                    then (
                    Lifts.raise(3)
                            with Wait(0.5)
                    )
                    then (
                    Kicker.pushOne()
                            with Wait(1.5)
                    )
                    then (
                    Kicker.pushOne()
                            with Wait(0.5)
                    )
                    then (
                    Lifts.raise(2)
                            with Wait(1.0)
                    )
                    then Kicker.pushOne()
                ) withEnd {
                    Lifts.resetLifts(Lifts.LiftPos.HOLD).command()
                    Launcher.speed = 0.0
                    MidtakeWheel.speed = 0.0
                    IntakeWheel.speed = 0.0
                }
        )

        // Drivetrain.pos = Pose2D(
        //     METER,
        //     0.0,
        //     0.0,
        //     DEGREES,
        //     0.0,
        // )
        Drivetrain.resetToStartPos()

        driver.apply {
            (left_trigger and x).onTrue(prime(3))
            (left_trigger and y).onTrue(prime(2))
            (left_trigger and b).onTrue(prime(1))
            (right_bumper and a).onTrue(Lifts.resetLifts())

            a.whileTrue(Kicker.gyrate(0.5))
            b.whileTrue(Kicker.gyrate(-0.5))

            left_trigger.onTrue(prime())

            right_trigger.onTrue(
                AdvancingCommandGroup(
                    drive,
                    ShootingState()
                ).also { it.schedule() }
            )

            right_trigger.whileTrue(MidtakeWheel.spin())

            left_bumper.whileTrue(
                drive.slowmode()
            )
        }
    }
}