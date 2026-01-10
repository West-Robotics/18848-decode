package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.component.Component
import org.firstinspires.ftc.teamcode.component.Gamepad
import org.firstinspires.ftc.teamcode.command.internal.Command
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
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import org.firstinspires.ftc.teamcode.util.Globals
import kotlin.math.*

object Drivetrain: Subsystem<Drivetrain>() {

    private val FRONT_STRAFE_MODIFIER = 0.74
    // private val FRONT_STRAFE_MODIFIER = 1.0

    private val frontLeft  = HardwareMap.frontLeft (FORWARD)
    private val frontRight = HardwareMap.frontRight(REVERSE)
    private val backLeft   = HardwareMap.backLeft  (FORWARD)
    private val backRight  = HardwareMap.backRight (REVERSE)
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
        // log("position string")value posString()
        log("position") value Pose2d(
            pos.getX(METER), pos.getY(METER),
            Rotation2d.fromRadians(pos.getHeading(RADIANS))
        )
        log("distance to goal") value this.distanceTo(Globals.alliance.goal)
    }


    init { // TODO: add pinpoint start vals
        pinpoint.xDirection = GoBildaPinpointDriver.EncoderDirection.REVERSED
        pinpoint.yDirection = GoBildaPinpointDriver.EncoderDirection.FORWARD
        pinpoint.podType = GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD
        // pinpoint.yOffset = -22.5
        // pinpoint.xOffset = -65.5
        pinpoint.yOffset = -110.0
        pinpoint.xOffset = 42.5
        pinpoint.pos = Globals.start_pos.get()
        components.filter { it is Motor }.forEach { (it as Motor).setZPB(DcMotor.ZeroPowerBehavior.BRAKE) }
    }

    var pos: Pose2D
        get() = pinpoint.pos
        set(value) {
            pinpoint.pos = value
        }

    fun getZone(): Zone = when (distanceTo(Globals.alliance.goal, DistanceUnit.INCH)) {
        in 0.0..24.0 -> Zone.NEAR_FRONT
        in 24.0..48.0 -> Zone.FAR_FRONT
        else -> Zone.BACKZONE
    }

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

    fun distanceTo(target: Pose2D, unit: DistanceUnit = METER) = sqrt(
        (target.getX(unit) - pinpoint.pos.getX(unit)).pow(2) +
        (target.getY(unit) - pinpoint.pos.getY(unit)).pow(2)
    )

    override fun disable() {
        // components.filter { it is Motor }.forEach { (it as Motor).effort = 0.0 }
        this.setSpeed(0.0)
    }

    override fun reset() {
        components.forEach { it.reset() }
        components.filter { it is Motor }.forEach { (it as Motor).setZPB(DcMotor.ZeroPowerBehavior.BRAKE) }
    }
}
