package org.firstinspires.ftc.teamcode.command


import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.command.internal.*
import org.firstinspires.ftc.teamcode.subsystems.*
import org.firstinspires.ftc.teamcode.RobotConstants.DRIVE_P
import org.firstinspires.ftc.teamcode.RobotConstants.DRIVE_I
import org.firstinspires.ftc.teamcode.RobotConstants.DRIVE_D
import org.firstinspires.ftc.teamcode.RobotConstants.TURN_P
import org.firstinspires.ftc.teamcode.RobotConstants.TURN_I
import org.firstinspires.ftc.teamcode.RobotConstants.TURN_D
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit.*
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.*
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D
import org.firstinspires.ftc.teamcode.util.log
import org.firstinspires.ftc.teamcode.util.PIDController
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
    val targetAngle = atan2(Drivetrain.pos.getY(METER) - point.getY(METER), Drivetrain.pos.getX(METER) - point.getX(METER)) + PI

    val error: Double get() =  targetAngle - Drivetrain.pos.getHeading(RADIANS)
    val pid_controller = PIDController(TURN_P, TURN_I, TURN_D)
    val timer = ElapsedTime()

    override fun execute(dt: Double) {
        Drivetrain.log("error") value error
        Drivetrain.log("target") value point.toPsiKit()
        Drivetrain.log("target angle") value targetAngle
        Drivetrain.log("current angle") value Drivetrain.pos.getHeading(RADIANS)
        val out: Double = pid_controller.calculate(error)
        Drivetrain.setSpeed(
            0.0,
            0.0,
            -out,
        )
        if (abs(error) >= errormargin) {
            timer.reset()
        }
    }

    override fun isFinished() = timer.seconds() >= 3.0 && abs(error) <= errormargin

    override fun end(interrupted: Boolean) {
        Drivetrain.setSpeed(0.0)
    }
}

class ForwardToPointCommand(val point: Pose2D, val errormargin: Double = 0.05) : Command(requirements = mutableSetOf(Drivetrain)) {
    val error: Double get() = Drivetrain.distanceTo(point)
    val pid_controller = PIDController(DRIVE_P, DRIVE_I, DRIVE_D)
    val timer = ElapsedTime()

    override fun execute(dt: Double) {
        Drivetrain.log("error") value error
        Drivetrain.log("target") value point.toPsiKit()
        val out: Double = pid_controller.calculate(error)
        Drivetrain.setSpeed(
            0.0,
            out,
            0.0,
        )
        if (abs(error) >= errormargin) {
            timer.reset()
        }
    }

    override fun isFinished() = timer.seconds() >= 1.0

    override fun end(interrupted: Boolean) {
        Drivetrain.setSpeed(0.0)
    }
}

fun MoveFancy(direction: Double, distance: Double) = (SpinCommand(direction) andThen ForwardCommand(distance))
// fun GotoPosition(point: Pose2D) = TurnToFaceCommand(point) andThen Wait(3.0).withRequirements(Drivetrain) andThen ForwardToPointCommand(point)
fun GotoPosition(point: Pose2D) = TurnToFaceCommand(point) andThen ForwardToPointCommand(point)

