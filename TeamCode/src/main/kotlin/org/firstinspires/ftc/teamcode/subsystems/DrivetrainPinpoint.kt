package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import org.firstinspires.ftc.teamcode.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.robertmkII.hardware.GoBildaPinpointDriver
import java.util.Locale

object Drivetrain {

    private val frontLeft = HardwareMap.frontLeft(DcMotorSimple.Direction.REVERSE, DcMotor.ZeroPowerBehavior.BRAKE)
    private val frontRight = HardwareMap.frontRight(DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE)
    private val backLeft = HardwareMap.backLeft(DcMotorSimple.Direction.REVERSE, DcMotor.ZeroPowerBehavior.BRAKE)
    private val backRight = HardwareMap.backRight(DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE)
    val pinpoint = HardwareMap.pinpoint(
        0.0, 0.0,
        GoBildaPinpointDriver.EncoderDirection.FORWARD,
        GoBildaPinpointDriver.EncoderDirection.FORWARD,
        GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD,
    )

    fun setSpeed(x: Double, y:Double, turn: Double) {
        frontLeft.effort = y - x - turn
        frontRight.effort = y + x + turn
        backLeft.effort = y + x - turn
        backRight.effort = y - x + turn
    }

    fun write() {
        frontLeft.write()
        frontRight.write()
        backLeft.write()
        backRight.write()
    }

    fun showPos(telemetry: Telemetry) {
        val pos = pinpoint.pos
        val data = String.format(Locale.US, "{X: %.3f, Y: %.3f, H: %.3f", pos.getX(DistanceUnit.MM),
            pos.getY(DistanceUnit.MM), pos.getHeading(AngleUnit.DEGREES))
        telemetry.addData("Position", data)
    }
}