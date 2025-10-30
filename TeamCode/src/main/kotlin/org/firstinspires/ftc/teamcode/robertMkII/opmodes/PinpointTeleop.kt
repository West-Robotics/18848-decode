package org.firstinspires.ftc.teamcode.robertMkII.opmodes

import com.qualcomm.hardware.lynx.LynxModule
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.robertMkII.hardware.NgGamepad
import org.firstinspires.ftc.teamcode.robertMkII.subsystems.DrivetrainPinpoint
import org.firstinspires.ftc.teamcode.robertMkII.subsystems.Launcher

/*
TODO:
 - lock in!
 - add subsystems for robot
 */

@TeleOp(name = "pinpoint")
class PinpointTeleop: LinearOpMode() {

    override fun runOpMode() {
//        val telemetry = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)

        val driver = NgGamepad(gamepad1)
        val operator = NgGamepad(gamepad2)

        val drivetrain = DrivetrainPinpoint(hardwareMap)
        val launcher = Launcher(hardwareMap)

        val looptime = ElapsedTime()

        val allHubs: List<LynxModule> = hardwareMap.getAll(LynxModule::class.java)
        for (hub in allHubs) {
            hub.bulkCachingMode = LynxModule.BulkCachingMode.MANUAL
        }

        waitForStart()
        while (opModeIsActive()) {
            looptime.reset()
            driver.update()
            operator.update()
            drivetrain.pinpoint.update()

            for (hub in allHubs) {
                hub.clearBulkCache()
            }

            drivetrain.setSpeed(
                -driver.left_stick_x,
                -driver.left_stick_y,
                -driver.right_stick_x
            )

            launcher.speed = driver.right_trigger


            drivetrain.write()
            launcher.write()

//            if (debug) {
//            }
            drivetrain.showPos(telemetry)
            telemetry.addData("Pinpoint Status", drivetrain.pinpoint.deviceStatus)
            telemetry.addData("Pinpoint Frequency", drivetrain.pinpoint.frequency)
            telemetry.addData("Rev Hub Frequency", 1/looptime.seconds())
            telemetry.update()
        }
    }

}
