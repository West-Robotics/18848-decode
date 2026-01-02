package org.firstinspires.ftc.teamcode.command

import org.firstinspires.ftc.teamcode.command.internal.Command
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain
import org.firstinspires.ftc.teamcode.subsystems.Subsystem
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.RobotConstants.DRIVE_P
import org.firstinspires.ftc.teamcode.RobotConstants.DRIVE_D

class GotoPositionCommand(val targetPos: Pose2D, val errormargin: Double = 0.05): Command() {

    private var prev_x_error = 0.0
    private var prev_y_error = 0.0
    private var prev_turn_error = 0.0

    override var requirements: MutableSet<Subsystem<*>> = mutableSetOf(Drivetrain)

    override fun initialize() {
        Drivetrain.setSpeed(0.0)
    }

    override fun execute(dt: Double) {

        val x_error = targetPos.getX(DistanceUnit.MM) - Drivetrain.pos.getX(DistanceUnit.MM)
        val y_error = targetPos.getY(DistanceUnit.MM) - Drivetrain.pos.getY(DistanceUnit.MM)
        val turn_error = targetPos.getHeading(AngleUnit.DEGREES) - Drivetrain.pos.getHeading(AngleUnit.DEGREES)

        Drivetrain.fieldCentricDrive(
            calculatepid(x_error, prev_x_error, dt),
            calculatepid(y_error, prev_y_error, dt),
            calculatepid(turn_error, prev_turn_error, dt)
        )

        prev_x_error = x_error
        prev_y_error = y_error
        prev_turn_error = turn_error
    }

    private fun calculatepid(error: Double, prev_error: Double, dt: Double): Double {
        val p = DRIVE_P * error
        val d = DRIVE_D * (error - prev_error) / (dt)

        return p + d
    }

    override fun end(interrupted: Boolean) {
        // TODO: add end functionality
        Drivetrain.setSpeed(0.0)
    }

    override fun isFinished(): Boolean {
        // TODO: actually check if it is finished
        return false
    }

    override var name = {
        "GotoPos: ${targetPos.getX(DistanceUnit.MM)}, ${targetPos.getY(DistanceUnit.MM)}, ${targetPos.getHeading(AngleUnit.DEGREES)}"
    }

}
