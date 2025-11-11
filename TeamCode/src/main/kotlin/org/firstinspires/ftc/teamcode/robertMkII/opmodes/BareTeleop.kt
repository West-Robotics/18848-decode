package org.firstinspires.ftc.teamcode.robertMkII.opmodes

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.qualcomm.hardware.lynx.LynxModule
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.robertMkII.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.robertMkII.hardware.NgGamepad
import org.firstinspires.ftc.teamcode.robertMkII.subsystems.TankDrivetrain
import org.firstinspires.ftc.teamcode.robertMkII.subsystems.Launcher
import kotlin.jvm.java

/*
TODO:
 - lock in!
 - add subsystems for robot
 */

@TeleOp(name = "drivetrain")
class BareTeleop: LinearOpMode() {

    override fun runOpMode() {
        val telemetry = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)
        HardwareMap.init(hardwareMap)

        val driver = NgGamepad(gamepad1)
        val operator = NgGamepad(gamepad2)

        val looptime = ElapsedTime()

        val allHubs: List<LynxModule> = hardwareMap.getAll(LynxModule::class.java)
        allHubs.forEach { it.bulkCachingMode = LynxModule.BulkCachingMode.MANUAL }

        waitForStart()
        while (opModeIsActive()) {
            looptime.reset()
            driver.update()
            operator.update()

            allHubs.forEach { it.clearBulkCache() }

            TankDrivetrain.setSpeed(
                -driver.left_stick_x,
                -driver.left_stick_y,
                -driver.right_stick_x
            )

//            launcher.speed = driver.right_trigger


            TankDrivetrain.write()
//            launcher.write()

//            if (debug) {
//            }
//            drivetrain.showPos(telemetry)
//            telemetry.addData("Pinpoint Status", drivetrain.pinpoint.deviceStatus)
//            telemetry.addData("Pinpoint Frequency", drivetrain.pinpoint.frequency)
            telemetry.addData("Rev Hub Frequency", 1/looptime.seconds())
            telemetry.update()
        }
    }

}
