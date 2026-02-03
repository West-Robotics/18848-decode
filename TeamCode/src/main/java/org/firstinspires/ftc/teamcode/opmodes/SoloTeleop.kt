package org.firstinspires.ftc.teamcode.opmodes

import com.qualcomm.robotcore.eventloop.opmode.*
import org.firstinspires.ftc.teamcode.command.*
import org.firstinspires.ftc.teamcode.command.internal.group.*
import org.firstinspires.ftc.teamcode.subsystems.*

@TeleOp(name = "solo?")
class SoloTeleop: CommandOpMode() {
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
                    ShootingState(),
                ).also { it.schedule() }
            )

            right_trigger.whileTrue(MidtakeWheel.spin())

            left_bumper.whileTrue(
                drive.slowmode()
            )
        }
    }
}
