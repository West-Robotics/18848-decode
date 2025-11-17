package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import org.firstinspires.ftc.teamcode.component.Component
import org.firstinspires.ftc.teamcode.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.robertmkII.hardware.GoBildaPinpointDriver
import java.util.Locale
import org.firstinspires.ftc.teamcode.component.Component.Direction.FORWARD
import org.firstinspires.ftc.teamcode.component.Component.Direction.REVERSE
import org.firstinspires.ftc.teamcode.subsystems.internal.Subsystem

object Drivetrain: Subsystem<Drivetrain>() {

    private val frontLeft = HardwareMap.frontLeft(REVERSE)
    private val frontRight = HardwareMap.frontRight(FORWARD)
    private val backLeft = HardwareMap.backLeft(REVERSE)
    private val backRight = HardwareMap.backRight(FORWARD)
    val pinpoint = HardwareMap.pinpoint(
        0.0, 0.0,
        GoBildaPinpointDriver.EncoderDirection.FORWARD,
        GoBildaPinpointDriver.EncoderDirection.FORWARD,
        GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD,
    )

    override val components: List<Component> = arrayListOf(
        frontLeft,
        frontRight,
        backLeft,
        backRight,
        pinpoint
    )

    fun setSpeed(x: Double, y:Double, turn: Double) {
        frontLeft.effort = y - x - turn
        frontRight.effort = y + x + turn
        backLeft.effort = y + x - turn
        backRight.effort = y - x + turn
    }

//    fun write() {
//        frontLeft.write()
//        frontRight.write()
//        backLeft.write()
//        backRight.write()
//    }

    fun showPos(telemetry: Telemetry) {
        val pos = pinpoint.pos
        val data = String.format(Locale.US, "{X: %.3f, Y: %.3f, H: %.3f", pos.getX(DistanceUnit.MM),
            pos.getY(DistanceUnit.MM), pos.getHeading(AngleUnit.DEGREES))
        telemetry.addData("Position", data)
    }
}