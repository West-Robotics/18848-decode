package org.firstinspires.ftc.teamcode.component

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D
import org.firstinspires.ftc.robotcore.external.navigation.UnnormalizedAngleUnit
import org.firstinspires.ftc.teamcode.robertmkII.hardware.GoBildaPinpointDriver

class Pinpoint(
    private val deviceSupplier: () -> GoBildaPinpointDriver?,
    private val xOffset: Double = 0.0,
    private val yOffset: Double = 0.0,
    private val xDirection: GoBildaPinpointDriver.EncoderDirection = GoBildaPinpointDriver.EncoderDirection.FORWARD,
    private val yDirection: GoBildaPinpointDriver.EncoderDirection = GoBildaPinpointDriver.EncoderDirection.FORWARD,
    private val encoderResolution: GoBildaPinpointDriver.GoBildaOdometryPods = GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD,
) {
    private var _pinpoint: GoBildaPinpointDriver? = null
    private val pinpoint: GoBildaPinpointDriver get() {
        if (_pinpoint == null) {
            _pinpoint = deviceSupplier() ?: error(
                "tryed to acces device before OpMode init"
            )
            _pinpoint!!.setOffsets(xOffset, yOffset, DistanceUnit.MM)
            _pinpoint!!.setEncoderResolution(encoderResolution)
            _pinpoint!!.setEncoderDirections(xDirection, yDirection)
            _pinpoint!!.resetPosAndIMU()
        }
        return _pinpoint!!
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
}

