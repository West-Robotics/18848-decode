package org.firstinspires.ftc.teamcode.robertMkII.opmodes

import com.qualcomm.hardware.lynx.LynxModule
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.robertMkII.hardware.GamepadButton
import org.firstinspires.ftc.teamcode.robertMkII.hardware.NgCRServo
import org.firstinspires.ftc.teamcode.robertMkII.hardware.NgGamepad
import org.firstinspires.ftc.teamcode.robertMkII.subsystems.Drivetrain

enum class Speed(val speed: Double) {
    OFF(0.0),
    FORWARD(1.0),
    BACKWARD(-1.0),
    MANUAL(0.0),
}

@TeleOp(name = "for league meet 1")
class IntakeTeleop : LinearOpMode() {

    override fun runOpMode() {
        val driver = NgGamepad(gamepad1)
        val operator = NgGamepad(gamepad2)

        val drivetrain = Drivetrain(hardwareMap)
        val lowspinner = NgCRServo(hardwareMap, "lowspinner", NgCRServo.ModelPWM.CR_AXON_MINI, DcMotorSimple.Direction.REVERSE)
        var spinnerSpeed = Speed.OFF
        var lastspinnerSpeed = Speed.OFF

        val looptime = ElapsedTime()

        val allHubs: List<LynxModule> = hardwareMap.getAll(LynxModule::class.java)
        for (hub in allHubs) {
            hub.bulkCachingMode = LynxModule.BulkCachingMode.MANUAL
        }

        waitForStart()
        looptime.reset()
        while (opModeIsActive()) {
            looptime.reset()
            driver.update()
            operator.update()

            for (hub in allHubs) {
                hub.clearBulkCache()
            }

            drivetrain.setSpeed(
                -driver.left_stick_x,
                -driver.left_stick_y,
                -driver.right_stick_x,
            )

            if (driver.once(GamepadButton.B)) {
                spinnerSpeed = Speed.BACKWARD
            } else if (driver.once(GamepadButton.A)) {
                spinnerSpeed = Speed.FORWARD
            } else if (driver.once(GamepadButton.X)) {
                spinnerSpeed = Speed.OFF
            } else if (driver.once(GamepadButton.Y)) {
                spinnerSpeed = Speed.MANUAL
            }

            if (spinnerSpeed != lastspinnerSpeed) {
                lowspinner.effort = spinnerSpeed.speed
                lastspinnerSpeed = spinnerSpeed
            }
            if (spinnerSpeed == Speed.MANUAL) {
                lowspinner.effort = driver.right_trigger - driver.left_trigger
            }

            drivetrain.write()
            lowspinner.write()

            telemetry.addLine("A = Intake")
            telemetry.addLine("B = Outtake")
            telemetry.addLine("X = OFF")
            telemetry.addLine("Y = Manual")
            telemetry.addLine("Right Trigger = Intake")
            telemetry.addLine("Left Trigger = Outtake")
            telemetry.addLine("")
            telemetry.addData("looptime", looptime.seconds())
            telemetry.addData("spinner state", spinnerSpeed)
            telemetry.update()

        }
    }
}
