package org.firstinspires.ftc.teamcode.opmodes

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.qualcomm.hardware.lynx.LynxModule
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.component.CRServo
import org.firstinspires.ftc.teamcode.component.GamepadButton
import org.firstinspires.ftc.teamcode.component.NgGamepad
import org.firstinspires.ftc.teamcode.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.subsystems.Launcher
import org.firstinspires.ftc.teamcode.subsystems.TankDrivetrain
import kotlin.jvm.java
import kotlin.math.pow
import kotlin.math.sign

/*
TODO:
 - lock in!
 - add subsystems for robot
 */

@TeleOp(name = "cooper is a hardware guy now")
class ScrimTeleop: LinearOpMode() {

    override fun runOpMode() {
        val telemetry = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)
        HardwareMap.init(hardwareMap)

        val driver = NgGamepad(gamepad1)
        val operator = NgGamepad(gamepad2)

        val kicker = HardwareMap.highspinner(DcMotorSimple.Direction.FORWARD)
        val kickerspeed = 0.5

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
                -sign(driver.left_stick_x)*driver.left_stick_x.pow(2),
                -sign(driver.left_stick_y)*driver.left_stick_y.pow(2),
                -sign(driver.right_stick_x)*driver.right_stick_x.pow(2),
            )

            if (driver.down(GamepadButton.A)) {
                kicker.effort = kickerspeed
            } else if (driver.down(GamepadButton.B)) {
                kicker.effort = -kickerspeed
            } else {
                kicker.effort = 0.0
            }


            Launcher.speed = driver.right_trigger - driver.left_trigger
            if (driver.down(GamepadButton.RIGHT_BUMPER)) Launcher.speed = 0.7


            TankDrivetrain.write()
            Launcher.write()
            kicker.write()

//            if (debug) {
//            }
//            drivetrain.showPos(telemetry)
//            telemetry.addData("Pinpoint Status", drivetrain.pinpoint.deviceStatus)
//            telemetry.addData("Pinpoint Frequency", drivetrain.pinpoint.frequency)
            telemetry.addData("kicker", kicker.effort)
            telemetry.addData("launcher speed", Launcher.speed)
            telemetry.addData("Rev Hub Frequency", 1/looptime.seconds())
            telemetry.update()
        }
    }

}
