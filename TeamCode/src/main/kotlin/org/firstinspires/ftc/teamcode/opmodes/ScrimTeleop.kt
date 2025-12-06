package org.firstinspires.ftc.teamcode.opmodes

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.command.internal.*
import org.firstinspires.ftc.teamcode.subsystems.*
import org.firstinspires.ftc.teamcode.subsystems.Zone.*

@TeleOp(name = "dont know dont care")
class ScrimTeleop: CommandOpMode() {
    override fun initialize() {

        fun prime(slot: Int) = (
            Lifts.resetLifts()
            andThen (
                IntakeWheel.spin() parallelTo 
                MidtakeWheel.spin() parallelTo
                Lifts.raise(slot)
            ) withTimeout 1.0
        ).withRequirements(IntakeWheel, MidtakeWheel, Lifts)
        .isInteruptable(false)
        // fun prime(slot: Int) = Lifts.raise(slot)

        Drivetrain.run {
            setSpeed(
                driver.left_stick.x.sq,
                -driver.left_stick.y.sq,
                driver.right_stick.x.sq
            )
        }.isInteruptable(false).schedule()

        Lifts.showPos()
        var zone: Zone = BACKZONE

        Telemetry.addAll {
            "zone" ids { zone }
            "launcher power" ids { Launcher.speed }
        }

        driver.apply {
            a.whileTrue(
                Kicker.gyrate(0.5)
            )
            b.whileTrue(
                Kicker.gyrate(-0.5)
            )
            x.onTrue(
                Lifts.resetLifts()
            )
            left_trigger.whileTrue(
                IntakeWheel.spin()
            )
            left_bumper.whileTrue(
                MidtakeWheel.spin()
            )
            right_bumper.whileTrue(
                Launcher.spin(zone)
            )
            right_trigger.apply {
                threshold = 0.01
                whileTrue(
                    Launcher.run {
                        speed = toDouble()
                    }
                )
            }
            dpad_left.onTrue(
                prime(1)
            )
            dpad_up.onTrue(
                prime(2)
            )
            dpad_right.onTrue(
                prime(3)
            )
            dpad_down.onTrue(
                Lifts.resetLifts()
            )
        }

        operator.apply {
            x.onTrue(
                prime(1)
            )
            y.onTrue(
                prime(2)
            )
            b.onTrue(
                prime(3)
            )
            a.onTrue(
                Lifts.resetLifts()
            )

            dpad_down.onTrue {
                zone = BACKZONE
            }
            dpad_left.onTrue {
                zone = FAR_FRONT
            }
            dpad_right.onTrue {
                zone = NEAR_FRONT
            }
        }
    }
}
