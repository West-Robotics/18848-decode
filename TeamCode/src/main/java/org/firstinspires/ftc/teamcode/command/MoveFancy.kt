package org.firstinspires.ftc.teamcode.command


import com.qualcomm.robotcore.util.*
import org.firstinspires.ftc.robotcore.external.navigation.*
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.*
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit.*
import org.firstinspires.ftc.teamcode.RobotConstants.DRIVE_D
import org.firstinspires.ftc.teamcode.RobotConstants.DRIVE_I
import org.firstinspires.ftc.teamcode.RobotConstants.DRIVE_P
import org.firstinspires.ftc.teamcode.RobotConstants.TURN_D
import org.firstinspires.ftc.teamcode.RobotConstants.TURN_I
import org.firstinspires.ftc.teamcode.RobotConstants.TURN_P
import org.firstinspires.ftc.teamcode.command.internal.*
import org.firstinspires.ftc.teamcode.component.*
import org.firstinspires.ftc.teamcode.subsystems.*
import org.firstinspires.ftc.teamcode.util.*
import kotlin.math.*


class TurnCommand(val targetAngle: Double, val errormargin: Double = 0.05) : Command(requirements = mutableSetOf(Drivetrain)) {
    val error: Double get() =  (targetAngle - Drivetrain.pos.getHeading(RADIANS)).let {
        var out = it
        while (out < -PI || out > PI) {
            if (out < -PI) {
                out += 2*PI
            } else if (out > PI) {
                out -= 2*PI
            } 
        }
        out
        // it
    }
    val pid_controller = PIDController(TURN_P, TURN_I, TURN_D)
    val timer = ElapsedTime()

    override fun execute(dt: Double) {
        val out: Double = pid_controller.calculate(error)
        Drivetrain.log("target position") value Pose2D(METER,Drivetrain.pos.getX(METER),Drivetrain.pos.getY(METER),RADIANS,targetAngle).toPsiKit()
        Drivetrain.log("error") value error
        Drivetrain.setSpeed(
            0.0,
            0.0,
            -out,
        )
        if (abs(error) >= errormargin) {
            pid_controller.i = 0.0
            timer.reset()
        }
    }

    override fun isFinished() = timer.seconds() >= 1.0 && abs(error) <= errormargin

    override fun end(interrupted: Boolean) {
        Drivetrain.setSpeed(0.0)
    }

}

fun TurnToFaceCommand(point: Pose2D, errormargin: Double = 0.05) = TurnCommand(
    targetAngle = Drivetrain.angleTo(point),
    errormargin = errormargin,
)

class MoveToPointCommand(val point: Pose2D, val direction: Component.Direction = Component.Direction.FORWARD, val errormargin: Double = 0.05) : Command(requirements = mutableSetOf(Drivetrain)) {
    // val error: Double get() = Drivetrain.distanceTo(point) * ( if (abs(Drivetrain.angleTo(point) - (Drivetrain.pos.getHeading(RADIANS) - (if (direction == Component.Direction.REVERSE) PI else 0.0))) > 1.4) 1 else -1)
    val error: Double get() = Drivetrain.distanceTo(point)
    val pid_controller = PIDController(DRIVE_P, DRIVE_I, DRIVE_D)
    val timer = ElapsedTime()

    override fun execute(dt: Double) {
        val out: Double = pid_controller.calculate(error) * direction.dir
        Drivetrain.log("target position") value point.toPsiKit()
        Drivetrain.log("error") value error
        Drivetrain.setSpeed(
            0.0,
            out,
            0.0,
        )
        if (abs(error) >= errormargin) {
            timer.reset()
        }
    }

    // override fun isFinished() = timer.seconds() >= 1.0
    override fun isFinished() = abs(error) <= errormargin

    override fun end(interrupted: Boolean) {
        Drivetrain.setSpeed(0.0)
    }
}

