package org.firstinspires.ftc.teamcode.robertMkII.opmodes

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.robertMkII.subsystems.TankDrivetrain

@Autonomous(name = "manually controlled auto")
class ForwardAuto : LinearOpMode() {
    override fun runOpMode() {
        waitForStart()
        while (opModeIsActive()) {
            TankDrivetrain.setSpeed(0.0,0.5,0.0)
            TankDrivetrain.write()
        }
    }

}