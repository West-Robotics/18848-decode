package org.firstinspires.ftc.teamcode.opmodes

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.command.internal.RunCommand
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain

@TeleOp(name = "drivetrain")
class BareTeleop: CommandOpMode() {

    override fun initialize() {

        Drivetrain.reset()
        Drivetrain.run {
            setSpeed(
                driver.left_stick.x.sq,
                -driver.left_stick.y.sq,
                driver.right_stick.x.sq
            )
        }.schedule()

    }

}
