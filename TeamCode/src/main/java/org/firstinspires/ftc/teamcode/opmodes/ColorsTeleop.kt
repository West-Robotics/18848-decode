package org.firstinspires.ftc.teamcode.opmodes

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.command.internal.*
import org.firstinspires.ftc.teamcode.command.*
import org.firstinspires.ftc.teamcode.subsystems.*
import org.firstinspires.ftc.teamcode.subsystems.Zone.*
import org.firstinspires.ftc.teamcode.util.Globals

@TeleOp(name = "smelling colors")
class ColorsTeleop: CommandOpMode() {

    override fun onStart() {

        val drive = TeleOpDrive(
            { driver.left_stick.x.cube },
            { -driver.left_stick.y.cube },
            { driver.right_stick.x.cube },
            0.9
        ).also { it.schedule() }

        // Lifts.showPos()
        var zone: Zone = BACKZONE
        var auto_zone: Boolean = false

        Telemetry.addAll {
            "zone" ids { zone }
            "zone management" ids { if (auto_zone) "auto" else "manual" }
            "launcher power" ids { Launcher.speed }
        }
        // Telemetry.show_commands = true

        // Color Sensors
        var primed: Boolean = false

        ColorSensors.sensors.forEach { sensor ->
            Trigger {
                sensor.hasBall
            }.onTrue {
                // If(
                //     { primed },
                //     PrimeSlot(ColorSensors.sensors.indexOf(sensor) + 1) 
                //         andThen InstantCommand { primed = true }
                // )
                Trigger {
                    !primed
                }.oneshot(false).onTrue(
                    PrimeSlot(ColorSensors.sensors.indexOf(sensor) + 1)
                        andThen InstantCommand { primed = true}
                )
            }
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
            y.whileTrue(
                Launcher.intake()
            )
            // left_bumper.whileTrue(
            //     MidtakeWheel.spin()
            // )
            left_bumper.whileTrue(
                drive.slowmode()
            )
            left_trigger.whileTrue(
                IntakeWheel.spin()
            )
            left_trigger.whileFalse(
                Lifts.resetLifts(Lifts.LiftPos.HOLD)
                    until { false }
                    withEnd Lifts.resetLifts(Lifts.LiftPos.LOW)
            )
            right_bumper.whileTrue(
                Launcher.run {
                    // spinFromDistance(Drivetrain.distanceTo(Globals.alliance.goal))
                }
            )
            // right_trigger.threshold = 0.01
            // right_trigger.whileTrue(
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
                PrimeSlot(1) andThen InstantCommand { primed = true }
            )
            dpad_up.onTrue(
                PrimeSlot(2) andThen InstantCommand { primed = true }
            )
            dpad_right.onTrue(
                PrimeSlot(3) andThen InstantCommand { primed = true }
            )
            dpad_down.onTrue(
                Lifts.resetLifts()
            )
        }

        operator.apply {
            // priming slots
            (left_bumper and x).onTrue(
                PrimeSlot(1) andThen InstantCommand { primed = true }
            )
            (left_bumper and y).onTrue(
                PrimeSlot(2) andThen InstantCommand { primed = true }
            )
            (left_bumper and b).onTrue(
                PrimeSlot(3) andThen InstantCommand { primed = true }
            )

            a.whileTrue(
                Kicker.gyrate(0.5)
            )
            b.whileTrue(
                Kicker.gyrate(-0.5)
            )
            x.onTrue(
                Lifts.resetLifts()
            )
            y.whileTrue(
                Launcher.intake()
            )

            left_trigger.whileTrue(
                IntakeWheel.spin()
            )
            left_trigger.whileFalse(
                Lifts.resetLifts(Lifts.LiftPos.HOLD)
                    until { false }
                    withEnd Lifts.resetLifts(Lifts.LiftPos.LOW)
            )
            right_bumper.whileTrue(
                MidtakeWheel.spin()
            )
            right_trigger.onTrue {
                primed = !primed
            }
            // right_trigger.whileTrue(
            //     Lifts.resetLifts(Lifts.LiftPos.HOLD) 
            //         until { false }
            //         withEnd { interrupted ->
            //             if (!interrupted) Lifts.resetLifts(Lifts.LiftPos.LOW).run()
            //         } withPriority Priority.LOW
            // )

            dpad_down.onTrue {
                zone = BACKZONE
            }
            dpad_left.onTrue {
                zone = FAR_FRONT
            }
            dpad_right.onTrue {
                zone = NEAR_FRONT
            }
            dpad_up.toggleOnTrue(
                Command()
                    withInit { auto_zone = true }
                    withExecute { zone = Drivetrain.getZone() }
                    withEnd { auto_zone = false }
                    withName "Auto Zoning"
            )
        }
    }
}
