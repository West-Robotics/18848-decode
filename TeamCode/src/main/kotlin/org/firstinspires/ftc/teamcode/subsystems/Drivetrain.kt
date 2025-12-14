package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.component.Component
import org.firstinspires.ftc.teamcode.hardware.HardwareMap
import java.util.Locale
import org.firstinspires.ftc.teamcode.component.Component.Direction.*
import org.firstinspires.ftc.teamcode.component.Motor
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D
import org.firstinspires.ftc.teamcode.util.log
import org.psilynx.psikit.core.wpi.math.Pose2d
import org.psilynx.psikit.core.wpi.math.Rotation2d
import org.psilynx.psikit.core.wpi.math.Translation2d
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.*
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit.*
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.max
import kotlin.math.abs

object Drivetrain: Subsystem<Drivetrain>() {

    private val FRONT_STRAFE_MODIFIER = 0.74
    // private val SLEW_RATE: Double? = 0.2
    private val SLEW_RATE: Double? = null

    private val frontLeft  = HardwareMap.frontLeft (FORWARD, SLEW_RATE)
    private val frontRight = HardwareMap.frontRight(REVERSE, SLEW_RATE)
    private val backLeft   = HardwareMap.backLeft  (FORWARD, SLEW_RATE)
    private val backRight  = HardwareMap.backRight (REVERSE, SLEW_RATE)
    val pinpoint = HardwareMap.pinpoint()

    override val components: List<Component> = arrayListOf(
        frontLeft,
        frontRight,
        backLeft,
        backRight,
        pinpoint
    )

    override fun update(dt: Double) {
        super.update(dt)
        log("position string")value posString()
        log("position") value Pose2d(
            pos.getX(METER), pos.getY(METER),
            Rotation2d.fromRadians(pos.getHeading(RADIANS))
        )
    }


    init { // TODO: add pinpoint start vals
        pinpoint.xDirection = GoBildaPinpointDriver.EncoderDirection.REVERSED
        pinpoint.yDirection = GoBildaPinpointDriver.EncoderDirection.FORWARD
        pinpoint.yOffset = 23.0
        pinpoint.xOffset = -152.0
        pinpoint.podType = GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD
        pinpoint.pos = Pose2D(
            MM,
            0.0,
            0.0,
            DEGREES,
            0.0
        )
        components.filter { it is Motor }.forEach { (it as Motor).setZPB(DcMotor.ZeroPowerBehavior.BRAKE) }
    }

    var pos: Pose2D
        get() = pinpoint.pos
        set(value) {
            pinpoint.pos = value
        }

    fun getZone(): Zone = Zone.BACKZONE

    fun fixedSpeed(x: Double, y: Double, turn: Double) = run {
        setSpeed(x, y, turn)
    } withEnd {
        setSpeed(0.0)
    }

    fun setSpeed(speed: Double) = setSpeed(speed, speed, speed)

    fun setSpeed(x: Double, y:Double, turn: Double) {
        val denominator: Double = max(abs(y)+abs(x)+abs(turn), 1.0)
        frontLeft.effort =  (y - x*FRONT_STRAFE_MODIFIER + turn)/denominator
        frontRight.effort = (y + x*FRONT_STRAFE_MODIFIER - turn)/denominator
        backLeft.effort =   (y + x                       + turn)/denominator
        backRight.effort =  (y - x                       - turn)/denominator
    }

    fun fieldCentricDrive(x: Double, y: Double, turn: Double) {
        // get heading
        val heading = pinpoint.pos.getHeading(RADIANS)
        val rotX = x * cos(heading) - y * sin(heading)
        val rotY = x * sin(heading) + y * cos(heading)
        setSpeed(rotX, rotY, turn)
    }

    fun posString(): String {
        val pos = pinpoint.pos
        return String.format(Locale.US, "{X: %.3f, Y: %.3f, H: %.3f", pos.getX(MM),
            pos.getY(MM), pos.getHeading(DEGREES))
    }

    override fun disable() {
        // components.filter { it is Motor }.forEach { (it as Motor).effort = 0.0 }
        this.setSpeed(0.0)
    }

    override fun reset() {
        components.forEach { it.reset() }
        components.filter { it is Motor }.forEach { (it as Motor).setZPB(DcMotor.ZeroPowerBehavior.BRAKE) }
    }
}
