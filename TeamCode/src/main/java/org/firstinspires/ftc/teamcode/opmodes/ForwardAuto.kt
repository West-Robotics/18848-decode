package org.firstinspires.ftc.teamcode.opmodes

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.command.internal.Command
import org.firstinspires.ftc.teamcode.command.internal.InstantCommand
import org.firstinspires.ftc.teamcode.command.internal.RunCommand
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain

@Autonomous(name = "manually controlled auto")
class ForwardAuto: CommandOpMode() {

    override fun onInit() {
        Drivetrain.run {
            setSpeed(0.0, 0.5, 0.0)
        }.schedule()
    }

}
