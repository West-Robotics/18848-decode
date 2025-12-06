package org.firstinspires.ftc.teamcode.opmodes

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import org.firstinspires.ftc.teamcode.command.internal.RunCommand
import org.firstinspires.ftc.teamcode.command.internal.TimedCommand
import org.firstinspires.ftc.teamcode.subsystems.Kicker
import org.firstinspires.ftc.teamcode.subsystems.Launcher
import org.firstinspires.ftc.teamcode.subsystems.TankDrivetrain

@Disabled
@Autonomous(name = "full auto?")
class ScrimAuto: CommandOpMode() {
    override fun initialize() {

        Launcher.run {
            speed = 0.7
        }.schedule()

        (TankDrivetrain.run {
            setSpeed(0.0, 0.5, 0.0)
        } withTimeout 0.7
        andThen Kicker.pushOne()
        andThen TimedCommand(0.3)
        andThen Kicker.pushOne()
        ).schedule()

    }
}
