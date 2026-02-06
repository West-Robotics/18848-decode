package org.firstinspires.ftc.teamcode.opmodes

import com.qualcomm.robotcore.eventloop.opmode.*
import org.firstinspires.ftc.teamcode.subsystems.*

@Autonomous(name = "manually controlled auto")
class ForwardAuto: CommandOpMode() {

    override fun onStart() {
        Drivetrain.resetToStartPos()
        Drivetrain.run {
            tankDrive(0.5,  0.0)
        }.schedule()
    }

}
