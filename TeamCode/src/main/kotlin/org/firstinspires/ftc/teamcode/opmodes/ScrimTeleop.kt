package org.firstinspires.ftc.teamcode.opmodes

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.command.internal.*
import org.firstinspires.ftc.teamcode.command.*
import org.firstinspires.ftc.teamcode.subsystems.*
import org.firstinspires.ftc.teamcode.subsystems.Zone.*

@TeleOp(name = "dont know dont care")
class ScrimTeleop: CommandOpMode() {

    override fun onInit() {

        var slow_mode = false

        Drivetrain.run {
            setSpeed(
                driver.left_stick.x.cube * (if (slow_mode) 0.5 else 1.0),
                -driver.left_stick.y.cube * (if (slow_mode) 0.5 else 1.0),
                driver.right_stick.x.cube * (if (slow_mode) 0.5 else 1.0),
            )
        }.withName("Drive").isInterruptable(false).schedule()

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
            x.whileTrue(
                MidtakeWheel.spin()
            )
            left_trigger.whileTrue(
                IntakeWheel.spin()
            )
            y.whileTrue(
                Launcher.run {
                    speed = -0.3
                }
            )
            // left_bumper.whileTrue(
            //     MidtakeWheel.spin()
            // )
            left_bumper.onTrue {
                slow_mode = true
            }
            left_bumper.onFalse {
                slow_mode = false
            }
            right_bumper.whileTrue(
                Launcher.run {
                    speed = zone.launcher_speed
                }
            )
            // right_trigger.apply {
            //     threshold = 0.01
            //     whileTrue(
            //         Launcher.run {
            //             speed = toDouble()
            //         }
            //     )
            // }
            Trigger {
                driver.right_trigger.toDouble() >= 0.01
            }.whileTrue(
                Launcher.run {
                    speed = driver.right_trigger.toDouble()
                }
            )
            dpad_left.onTrue(
                PrimeSlot(1)
            )
            dpad_up.onTrue(
                PrimeSlot(2)
            )
            dpad_right.onTrue(
                PrimeSlot(3)
            )
            dpad_down.onTrue(
                Lifts.resetLifts()
            )
        }

        operator.apply {
            x.onTrue(
                PrimeSlot(1)
            )
            y.onTrue(
                PrimeSlot(2)
            )
            b.onTrue(
                PrimeSlot(3)
            )
            a.onTrue(
                Lifts.resetLifts()
            )

            left_trigger.whileTrue(
                IntakeWheel.spin()
            )
            left_bumper.whileTrue(
                MidtakeWheel.spin()
            )
            right_bumper.whileTrue(
                MidtakeWheel.reverse()
            )
            right_trigger.whileTrue(
                Lifts.resetLifts(Lifts.LiftPos.HOLD) 
                    until { false }
                    withEnd Lifts.resetLifts(Lifts.LiftPos.LOW)
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
