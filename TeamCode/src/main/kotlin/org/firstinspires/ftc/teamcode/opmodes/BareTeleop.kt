package org.firstinspires.ftc.teamcode.opmodes

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.command.internal.RunCommand
import org.firstinspires.ftc.teamcode.subsystems.*

@TeleOp(name = "drivetrain")
class BareTeleop: CommandOpMode() {
    override fun onInit() {
        Drivetrain.reset()
        Drivetrain.run {
            setSpeed(
                driver.left_stick.x.sq,
                -driver.left_stick.y.sq,
                driver.right_stick.x.sq
            )
        }.schedule()

        driver.apply {
            x.onTrue(
                Lifts.resetLifts(Lifts.LiftPos.ZERO)
            )
            y.onTrue(
                Lifts.resetLifts(Lifts.LiftPos.LOW)
            )
            b.onTrue(
                Lifts.resetLifts(Lifts.LiftPos.HIGH)
            )
        }
    }
}
