package org.firstinspires.ftc.teamcode.opmodes

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.command.internal.*
import org.firstinspires.ftc.teamcode.command.*
import org.firstinspires.ftc.teamcode.subsystems.*
import org.firstinspires.ftc.teamcode.hardware.HardwareMap

@TeleOp(name = "testing!")
class TestTeleop: CommandOpMode() {
    override fun onInit() {
        // TeleOpDrive(
        //     { driver.left_stick.x.sq },
        //     { -driver.left_stick.y.sq },
        //     { driver.right_stick.x.sq },
        //     0.8
        // ).schedule()

        var primed: Boolean = false

        fun prime(slot: Int) = (
            Lifts.raise(slot)
            with (
                IntakeWheel.spin() with
                MidtakeWheel.spin()
            ) withTimeout 0.5
        ) andThen InstantCommand { primed = true }

        Trigger {
            ColorSensors.sensors[0].hasBall
        }.onTrue(
            If({ !primed }, prime(1))
        )


        // val testing_pin = HardwareMap.testing_pin(maxDist = 360.0)
        // val distance_pin = HardwareMap.testing_pin(maxDist = 100.0)
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
        }
    }
}
