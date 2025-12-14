package org.firstinspires.ftc.teamcode.opmodes

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import org.firstinspires.ftc.teamcode.command.internal.RunCommand
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain
import org.firstinspires.ftc.teamcode.subsystems.Telemetry

@Disabled
@TeleOp(name = "Pinpoint be testing")
class PinpointTest: CommandOpMode() {

    override fun onInit() {

        Drivetrain.run {
            fieldCentricDrive(
                driver.left_stick.x.sq,
                -driver.left_stick.y.sq,
                driver.right_stick.x.sq
            )
        }.schedule()

        Telemetry.addAll {
            "position" ids Drivetrain::posString
            "pinpoint status" ids Drivetrain.pinpoint::status
        }

    }

}
