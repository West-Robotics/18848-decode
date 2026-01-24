package org.firstinspires.ftc.teamcode.opmodes

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.command.internal.*
import org.firstinspires.ftc.teamcode.command.*
import org.firstinspires.ftc.teamcode.subsystems.*
import org.firstinspires.ftc.teamcode.hardware.HardwareMap

@TeleOp(name = "testing!")
class TestTeleop: CommandOpMode() {
    override fun onStart() {
        // TeleOpDrive(
        //     { driver.left_stick.x.sq },
        //     { -driver.left_stick.y.sq },
        //     { driver.right_stick.x.sq },
        //     0.8
        // ).schedule()

        var primed: Boolean = false

        fun prime(slot: Int) = (
            Lifts.raise(slot)
            andThen (
                IntakeWheel.spin() with
                MidtakeWheel.spin()
            ) withTimeout 0.5
            andThen MidtakeWheel.spin() withTimeout(1.5)
            andThen Lifts.lower(slot)
        ) andThen InstantCommand { primed = true }


        ColorSensors.sensors.forEach { sensor ->
            Trigger {
                sensor.hasBall
            }.onTrue(
                If(
                    { !primed },
                    prime(ColorSensors.sensors.indexOf(sensor) + 1),
                    InstantCommand {
                        Trigger({ !primed }).oneshot(true).onTrue(
                            prime(ColorSensors.sensors.indexOf(sensor) + 1),
                        )
                    }
                )
            )
        }


        // val testing_pin = HardwareMap.testing_pin(maxDist = 360.0)
        // val distance_pin = HardwareMap.testing_pin(maxDist = 100.0)
        Telemetry.show_commands = true
        Telemetry.addAll {
            "color sensor 1" ids { ColorSensors.sensors[0].color }
            "color sensor 2" ids { ColorSensors.sensors[1].color }
            "color sensor 3" ids { ColorSensors.sensors[2].color }
            "primed"         ids { primed }
            // "hsv value" ids { testing_pin.position }
            // "distance" ids { distance_pin.position }
        }

        driver.apply {
            a.onTrue {
                primed = !primed
            }
            b.onTrue(
                Lifts.resetLifts()
            )
            x.whileTrue(
                IntakeWheel.spin()
            )
            y.whileTrue(MidtakeWheel.spin())
        }
    }
}
