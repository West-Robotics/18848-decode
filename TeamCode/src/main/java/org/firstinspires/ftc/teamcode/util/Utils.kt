package org.firstinspires.ftc.teamcode.util

import org.firstinspires.ftc.robotcore.external.navigation.Pose2D
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit.METER
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.RADIANS
import org.psilynx.psikit.core.wpi.math.Pose2d
import org.psilynx.psikit.core.wpi.math.Rotation2d

fun controlEffort(targetPos: Double, currentPos: Double, kp: Double, feedforward: Double = 0.0) = kp*(targetPos-currentPos) + feedforward

fun Pose2D.toPsiKit() = Pose2d(
            this.getX(METER), this.getY(METER),
            Rotation2d.fromRadians(this.getHeading(RADIANS))
        )
