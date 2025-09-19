package org.firstinspires.ftc.teamcode.robertMkII.opmodes

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime


@Autonomous(name = "blank auto", preselectTeleOp = "NgTele")
class BlankAuto: LinearOpMode() {

    override fun runOpMode() {
        val e = ElapsedTime()
        waitForStart()
        e.reset()
        while (opModeIsActive()) {
            telemetry.addLine((30 - e.seconds()).toString())
            telemetry.update()
        }
    }
}