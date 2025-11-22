package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import org.firstinspires.ftc.teamcode.component.Component
import org.firstinspires.ftc.teamcode.hardware.HardwareMap
import java.util.Locale
import org.firstinspires.ftc.teamcode.component.Component.Direction.FORWARD
import org.firstinspires.ftc.teamcode.component.Component.Direction.REVERSE
import org.firstinspires.ftc.teamcode.subsystems.internal.Subsystem
import org.firstinspires.ftc.teamcode.component.Motor
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.max
import kotlin.math.abs

object Drivetrain: Subsystem() {

    private val frontLeft = HardwareMap.frontLeft(REVERSE)
    private val frontRight = HardwareMap.frontRight(FORWARD)
    private val backLeft = HardwareMap.backLeft(REVERSE)
    private val backRight = HardwareMap.backRight(FORWARD)
    val pinpoint = HardwareMap.pinpoint()

    override val components: List<Component> = arrayListOf(
        frontLeft,
        frontRight,
        backLeft,
        backRight,
        pinpoint
    )


    init { // TODO: add pinpoint init vals
        pinpoint.xDirection = GoBildaPinpointDriver.EncoderDirection.REVERSED
        pinpoint.yDirection = GoBildaPinpointDriver.EncoderDirection.FORWARD
        pinpoint.xOffset = 23.0
        pinpoint.yOffset = -152.0
        pinpoint.podType = GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD
        pinpoint.pos = Pose2D(
            DistanceUnit.MM,
            0.0,
            0.0,
            AngleUnit.DEGREES,
            0.0
        )
        components.filter { it is Motor }.forEach { (it as Motor).setZPB(DcMotor.ZeroPowerBehavior.BRAKE) }
    }

    var pos: Pose2D
        get() = pinpoint.pos
        set(value) {
            pinpoint.pos = value
        }

    fun setSpeed(x: Double, y:Double, turn: Double) {
        val denominator: Double = max(abs(y)+abs(x)+abs(turn), 1.0)
        frontLeft.effort = (y - x - turn)/denominator
        frontRight.effort = (y + x + turn)/denominator
        backLeft.effort = (y + x - turn)/denominator
        backRight.effort = (y - x + turn)/denominator
    }

    fun fieldCentricDrive(x: Double, y: Double, turn: Double) {
        // get heading
        val heading = pinpoint.pos.getHeading(AngleUnit.RADIANS)
        val rotX = x * cos(heading) - y * sin(heading)
        val rotY = x * sin(heading) + y * cos(heading)
        setSpeed(rotX, rotY, turn)
    }

    fun posString(): String {
        val pos = pinpoint.pos
        return String.format(Locale.US, "{X: %.3f, Y: %.3f, H: %.3f", pos.getX(DistanceUnit.MM),
            pos.getY(DistanceUnit.MM), pos.getHeading(AngleUnit.DEGREES))
    }

    override fun disable() {
        components.filter { it is Motor }.forEach { (it as Motor).effort = 0.0 }
    }
}
