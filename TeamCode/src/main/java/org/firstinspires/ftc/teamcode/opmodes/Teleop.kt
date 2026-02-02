package org.firstinspires.ftc.teamcode.opmodes

import com.qualcomm.robotcore.eventloop.opmode.*
import org.firstinspires.ftc.teamcode.command.*
import org.firstinspires.ftc.teamcode.command.internal.group.*
import org.firstinspires.ftc.teamcode.subsystems.*
import org.firstinspires.ftc.teamcode.subsystems.Launcher.speed
import org.firstinspires.ftc.teamcode.subsystems.Launcher.spinFromDistance
import org.firstinspires.ftc.teamcode.subsystems.Zone.*
import org.firstinspires.ftc.teamcode.util.*

@TeleOp(name = "smelling colors")
class Teleop: CommandOpMode() {

    override fun onStart() {

        val drive = TeleOpDrive(
            { driver.left_stick.x.cube },
            { -driver.left_stick.y.cube },
            { driver.right_stick.x.cube },
            0.95
            // null,
        ).also { it.schedule() }

        var zone: Zone = BACKZONE
        var auto_zone: Boolean = false

        Telemetry.addAll {
            "zone" ids { zone }
            "zone management" ids { if (auto_zone) "auto" else "manual" }
            "launcher power" ids Launcher::speed
            // "color sensor 1" ids ColorSensors.sensors[0]::color
            // "color sensor 2" ids ColorSensors.sensors[1]::color
            // "color sensor 3" ids ColorSensors.sensors[2]::color
        }
        // Telemetry.show_commands = true

        Lights.display(Lights.DisplayType.ALIGNED_WITH_GOAL, Lights.DisplayType.BALLS_HELD).schedule()

        driver.apply {
            // a.whileTrue(Kicker.gyrate(0.5))
            // b.whileTrue(Kicker.gyrate(-0.5))
            // x.whileTrue(MidtakeWheel.spin())
            y.whileTrue(Launcher.intake())

            (left_bumper or right_bumper).whileTrue(drive.slowmode())
            right_trigger.whileTrue(
                IntakeWheel.spin()
                with MidtakeWheel.reverse()
            ).whileFalse(
                Lifts.resetLifts(Lifts.LiftPos.HOLD)
                    until { false }
                    withEnd Lifts.resetLifts(Lifts.LiftPos.LOW)
            )
            // right_bumper.whileTrue(
            //     Launcher.run {
            //         speed = zone.launcher_speed
            //     }
            // )
            // Trigger {
            //     driver.right_trigger.toDouble() >= 0.01
            // }.whileTrue(
            //     Launcher.run {
            //         speed = driver.right_trigger.toDouble()
            //     }
            // )

            // dpad_left.onTrue(prime(1))
            // dpad_up.onTrue(prime(2))
            // dpad_right.onTrue(prime(3))
            dpad_down.onTrue(Lifts.resetLifts(Lifts.LiftPos.HOLD))
        }

        operator.apply {
            // priming slots
            (left_trigger and x).onTrue(prime(3))
            (left_trigger and y).onTrue(prime(2))
            (left_trigger and b).onTrue(prime(1))
            (right_bumper and a).onTrue(Lifts.resetLifts())

            a.whileTrue(Kicker.gyrate(0.5))
            b.whileTrue(Kicker.gyrate(-0.5))
            // y.onTrue(Lifts.resetLifts())


            left_trigger.onTrue(prime())

            left_bumper.whileTrue(MidtakeWheel.spin())
            right_trigger.whileTrue(
                Launcher.run {
                    if (auto_zone) {
                        spinFromDistance(Drivetrain.distanceTo(Globals.alliance.goal))
                    } else {
                        speed = zone.launcher_speed
                    }
                }
            )
            // (
            //     right_bumper and
            //     Trigger {
            //         right_trigger.toDouble() >= 0.01
            //     }
            // ).whileTrue(
            //     Launcher.run {
            //         speed = right_trigger.toDouble()
            //     }
            // )

            dpad_down.onTrue { zone = BACKZONE }
            dpad_left.onTrue { zone = FAR_FRONT }
            dpad_right.onTrue { zone = NEAR_FRONT }
            dpad_up.onTrue { auto_zone = !auto_zone }
//            dpad_up.toggleOnTrue(
//                Command()
//                        withInit { auto_zone = true }
//                        withExecute { zone = Drivetrain.getZone() }
//                        withEnd { auto_zone = false }
//                        withName "Auto Zoning"
//            )
        }
    }
}
