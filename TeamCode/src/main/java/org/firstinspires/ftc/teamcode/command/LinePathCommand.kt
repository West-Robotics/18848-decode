package org.firstinspires.ftc.teamcode.command


import org.firstinspires.ftc.teamcode.command.internal.*
import org.firstinspires.ftc.teamcode.subsystems.*
import org.firstinspires.ftc.teamcode.RobotConstants.TURN_P
import org.firstinspires.ftc.teamcode.RobotConstants.DRIVE_P
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit.*
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.*
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D
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
        Drivetrain.setSpeed(
            0.0,
            0.0,
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

fun MoveFancy(direction: Double, distance: Double) = (SpinCommand(direction) andThen ForwardCommand(distance))

