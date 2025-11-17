package org.firstinspires.ftc.teamcode.opmodes

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.subsystems.TankDrivetrain

@Autonomous(name = "manually controlled auto")
class ForwardAuto : LinearOpMode() {
    override fun runOpMode() {
        HardwareMap.init(hardwareMap)
        waitForStart()
        TankDrivetrain.setSpeed(0.0,0.3,0.0)
        TankDrivetrain.write()
        while (opModeIsActive()) {}
    }

}