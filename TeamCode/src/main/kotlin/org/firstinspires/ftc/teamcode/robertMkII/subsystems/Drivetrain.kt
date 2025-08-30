package org.firstinspires.ftc.teamcode.robertMkII.subsystems

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import org.firstinspires.ftc.teamcode.GoBildaPinpointDriver
import org.firstinspires.ftc.teamcode.robertMkII.hardware.NgMotor
import java.util.Locale

class Drivetrain(hardwareMap: HardwareMap) {

    private val frontLeft = NgMotor(hardwareMap, "frontLeft", DcMotorSimple.Direction.REVERSE, DcMotor.ZeroPowerBehavior.BRAKE)
    private val frontRight = NgMotor(hardwareMap, "frontRight", DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE)
    private val backLeft = NgMotor(hardwareMap, "backLeft", DcMotorSimple.Direction.REVERSE, DcMotor.ZeroPowerBehavior.BRAKE)
    private val backRight = NgMotor(hardwareMap, "backRight", DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE)
    val pinpoint: GoBildaPinpointDriver = hardwareMap.get("pinpoint") as GoBildaPinpointDriver

    init {
        pinpoint.setOffsets(0.0, 0.0, DistanceUnit.MM) // TODO: fill in actual offsets
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD) // TODO: fill in actual directions
        pinpoint.resetPosAndIMU()
    }

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

    fun showPos(telemetry: MultipleTelemetry) {
        val pos = pinpoint.position
        val data = String.format(Locale.US, "{X: %.3f, Y: %.3f, H: %.3f", pos.getX(DistanceUnit.MM),
            pos.getY(DistanceUnit.MM), pos.getHeading(AngleUnit.DEGREES))
        telemetry.addData("Position", data)
    }
}