package org.firstinspires.ftc.teamcode.robertMkII.subsystems

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D
import org.firstinspires.ftc.robotcore.external.navigation.UnnormalizedAngleUnit
import org.firstinspires.ftc.teamcode.robertMkII.hardware.NgMotor
import org.firstinspires.ftc.teamcode.robertmkII.hardware.GoBildaPinpointDriver
import java.util.Locale

class DrivetrainPinpoint(hardwareMap: HardwareMap) {

    private val frontLeft = NgMotor(hardwareMap, "frontLeft", DcMotorSimple.Direction.REVERSE, DcMotor.ZeroPowerBehavior.BRAKE)
    private val frontRight = NgMotor(hardwareMap, "frontRight", DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE)
    private val backLeft = NgMotor(hardwareMap, "backLeft", DcMotorSimple.Direction.REVERSE, DcMotor.ZeroPowerBehavior.BRAKE)
    private val backRight = NgMotor(hardwareMap, "backRight", DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE)
    val pinpoint: GoBildaPinpointDriver = hardwareMap.get(GoBildaPinpointDriver::class.java, "pinpoint")

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

    val pos: Pose2D
        get() = Pose2D(
            DistanceUnit.MM,
            pinpoint.getPosX(DistanceUnit.MM),
            pinpoint.getPosY(DistanceUnit.MM),
            AngleUnit.DEGREES,
            pinpoint.getHeading(AngleUnit.DEGREES)
            )

    val vel: Pose2D
        get() = Pose2D(
            DistanceUnit.MM,
            pinpoint.getVelX(DistanceUnit.MM),
            pinpoint.getVelY(DistanceUnit.MM),
            AngleUnit.DEGREES,
            pinpoint.getHeadingVelocity(UnnormalizedAngleUnit.DEGREES)
        )


    fun showPos(telemetry: Telemetry) {
        val pos = pinpoint.position
        val data = String.format(Locale.US, "{X: %.3f, Y: %.3f, H: %.3f", pos.getX(DistanceUnit.MM),
            pos.getY(DistanceUnit.MM), pos.getHeading(AngleUnit.DEGREES))
        telemetry.addData("Position", data)
    }
}