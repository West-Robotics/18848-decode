package org.firstinspires.ftc.teamcode.robertMkII.opmodes

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.robertMkII.subsystems.Drivetrain

@Autonomous(name = "manually controlled auto")
class ForwardAuto : LinearOpMode() {
    override fun runOpMode() {
        val drivetrain = Drivetrain(hardwareMap)

        waitForStart()
        while (opModeIsActive()) {
            drivetrain.setSpeed(0.0,0.5,0.0)
            drivetrain.write()
        }
    }

}