package org.firstinspires.ftc.teamcode.opmodes

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.command.internal.*
import org.firstinspires.ftc.teamcode.command.*
import org.firstinspires.ftc.teamcode.subsystems.*
import org.firstinspires.ftc.teamcode.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit.METER
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES

@TeleOp(name = "testing!")
class TestTeleop: CommandOpMode() {
    override fun onStart() {
        val drive = TeleOpDrive(
            { driver.left_stick.x.sq },
            { -driver.left_stick.y.sq },
            { driver.right_stick.x.sq },
            0.9
        ).also { it.schedule() }

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
            a.whileTrue(Kicker.gyrate(0.5))
            b.whileTrue(Kicker.gyrate(-0.5))
            left_bumper.whileTrue(
                drive.slowmode()
            )
        }
    }
}
