package org.firstinspires.ftc.teamcode.component

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D
import org.firstinspires.ftc.robotcore.external.navigation.UnnormalizedAngleUnit
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver

class Pinpoint(
    private val deviceSupplier: () -> GoBildaPinpointDriver?,
    // private val xOffset: Double = 0.0,
    // private val yOffset: Double = 0.0,
    // private val xDirection: GoBildaPinpointDriver.EncoderDirection = GoBildaPinpointDriver.EncoderDirection.FORWARD,
    // private val yDirection: GoBildaPinpointDriver.EncoderDirection = GoBildaPinpointDriver.EncoderDirection.FORWARD,
    // private val encoderResolution: GoBildaPinpointDriver.GoBildaOdometryPods = GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD,
): Component() {
    private var _pinpoint: GoBildaPinpointDriver? = null
    private val pinpoint: GoBildaPinpointDriver get() {
        if (_pinpoint == null) {
            _pinpoint = deviceSupplier() ?: error(
                "tryed to acces device before OpMode init"
            )
            // _pinpoint!!.setOffsets(xOffset, yOffset, DistanceUnit.INCH)
            // _pinpoint!!.setEncoderResolution(encoderResolution)
            // _pinpoint!!.setEncoderDirections(xDirection, yDirection)
            _pinpoint!!.resetPosAndIMU()
        }
        return _pinpoint!!
    }
    private var _pos: Pose2D = pinpoint.position
    var pos: Pose2D
        get() = _pos
        set(value) {
            pinpoint.setPosition(value)
        }

    var vel: Pose2D = Pose2D(
            DistanceUnit.INCH,
            pinpoint.getVelX(DistanceUnit.INCH),
            pinpoint.getVelY(DistanceUnit.INCH),
            AngleUnit.DEGREES,
            pinpoint.getHeadingVelocity(UnnormalizedAngleUnit.DEGREES),
    )
        internal set

    var yOffset: Double = 0.0
        set(value) {
            field = value
            pinpoint.setOffsets(xOffset, value, DistanceUnit.INCH)
        }

    var xOffset: Double = 0.0
        set(value) {
            field = value
            pinpoint.setOffsets(value, yOffset, DistanceUnit.INCH)
        }

    var podType: GoBildaPinpointDriver.GoBildaOdometryPods = GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD
        set(value) {
            field = value
            pinpoint.setEncoderResolution(value)
        }

    var xDirection: GoBildaPinpointDriver.EncoderDirection = GoBildaPinpointDriver.EncoderDirection.FORWARD
        set(value) {
            field = value
            pinpoint.setEncoderDirections(value, yDirection)
        }

    var yDirection: GoBildaPinpointDriver.EncoderDirection = GoBildaPinpointDriver.EncoderDirection.FORWARD
        set(value) {
            field = value
            pinpoint.setEncoderDirections(xDirection, value)
        }

    override fun write() { }

    override fun reset() {
        pinpoint.resetPosAndIMU()
    }

    override fun update(dt: Double) {
        pinpoint.update()
        _pos = Pose2D(
            DistanceUnit.INCH,
            pinpoint.getPosX(DistanceUnit.INCH),
            pinpoint.getPosY(DistanceUnit.INCH),
            AngleUnit.DEGREES,
            pinpoint.getHeading(AngleUnit.DEGREES)
        )
        vel = Pose2D(
            DistanceUnit.INCH,
            pinpoint.getVelX(DistanceUnit.INCH),
            pinpoint.getVelY(DistanceUnit.INCH),
            AngleUnit.DEGREES,
            pinpoint.getHeadingVelocity(UnnormalizedAngleUnit.DEGREES),
        )
    }

    fun status() = pinpoint.getDeviceStatus()
}

