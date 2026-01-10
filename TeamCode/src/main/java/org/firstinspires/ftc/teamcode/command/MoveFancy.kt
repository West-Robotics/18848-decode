package org.firstinspires.ftc.teamcode.command


import org.firstinspires.ftc.teamcode.command.internal.*
import org.firstinspires.ftc.teamcode.subsystems.*
import org.firstinspires.ftc.teamcode.RobotConstants.TURN_P
import org.firstinspires.ftc.teamcode.RobotConstants.DRIVE_P
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit.*
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.*
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D
import org.firstinspires.ftc.teamcode.util.log
import org.firstinspires.ftc.teamcode.util.toPsiKit
import kotlin.math.*


class SpinCommand(private val direction: Double, val errormargin: Double = 0.1) : Command() {
    override var requirements = mutableSetOf<Subsystem<*>>(Drivetrain)

    override fun initialize() { }

    override fun execute(dt: Double) {
        val error = direction - Drivetrain.pos.getHeading(DEGREES)
        if (abs(error) < errormargin) {
            this.cancel()
            println(error)
        }
        val out = TURN_P * error
        Drivetrain.setSpeed( 0.0, 0.0,
            out
        )
    }

    override fun isFinished() = false
}

class ForwardCommand(distance: Double, val errormargin: Double = 0.01) : Command() {
    override var requirements = mutableSetOf<Subsystem<*>>(Drivetrain)

    private val targetPos = Pose2D(
        METER,
        Drivetrain.pos.getX(METER) + (distance * cos(Drivetrain.pos.getHeading(RADIANS))),
        Drivetrain.pos.getY(METER) + (distance * sin(Drivetrain.pos.getHeading(RADIANS))),
        DEGREES,
        Drivetrain.pos.getHeading(DEGREES)
    )

    override fun execute(dt: Double) {
        val error = Drivetrain.distanceTo(targetPos)
        if (abs(error) < errormargin) {
            this.cancel()
            println(error)
        }
        val out = DRIVE_P * error
        Drivetrain.setSpeed(
            0.0,
            out,
            0.0,
        )
    }

    override fun isFinished() = false

}

class TurnToFaceCommand(val point: Pose2D, val errormargin: Double = 0.05) : Command(requirements = mutableSetOf(Drivetrain)) {
    val targetAngle = (atan2(Drivetrain.pos.getY(METER) - point.getY(METER), Drivetrain.pos.getX(METER) - point.getX(METER)) - PI).let {
    // val targetAngle = atan2(Drivetrain.pos.getX(METER) - point.getX(METER), Drivetrain.pos.getY(METER) - point.getY(METER)).let {
        var out = it
        while (abs(out) > 2*PI) {
            out = abs(out) - 2*PI * sign(out)
        }
        out
    }
    val error: Double get() = targetAngle - Drivetrain.pos.getHeading(RADIANS) 
    override fun execute(dt: Double) {
        Drivetrain.log("error") value error
        Drivetrain.log("target") value point.toPsiKit()
        Drivetrain.log("target angle") value targetAngle
        Drivetrain.log("current angle") value Drivetrain.pos.getHeading(RADIANS)
        val out: Double = TURN_P * error
        Drivetrain.setSpeed(
            0.0,
            0.0,
            out,
        )
    }

    override fun isFinished() = abs(error) <= errormargin
}

class ForwardToPointCommand(val point: Pose2D, val errormargin: Double = 0.05) : Command(requirements = mutableSetOf(Drivetrain)) {
    val error: Double get() = Drivetrain.distanceTo(point)

    override fun execute(dt: Double) {
        Drivetrain.log("error") value error
        Drivetrain.log("target") value point.toPsiKit()
        val out: Double = DRIVE_P * error
        Drivetrain.setSpeed(
            0.0,
            out,
            0.0,
        )
    }

    override fun isFinished() = abs(error) <= errormargin
}

fun MoveFancy(direction: Double, distance: Double) = (SpinCommand(direction) andThen ForwardCommand(distance))
fun GotoPosition(point: Pose2D) = (TurnToFaceCommand(point) andThen ForwardToPointCommand(point))

