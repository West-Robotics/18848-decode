package org.firstinspires.ftc.teamcode.opmodes

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.command.internal.RunCommand
import org.firstinspires.ftc.teamcode.command.internal.WaitCommand
import org.firstinspires.ftc.teamcode.subsystems.Kicker
import org.firstinspires.ftc.teamcode.subsystems.Launcher
import org.firstinspires.ftc.teamcode.subsystems.TankDrivetrain

@Autonomous(name = "full auto?")
class ScrimAuto: CommandOpMode() {
    override fun initialize() {

        fun pushOne() = RunCommand(Kicker) {
            Kicker.speed = 0.5
        } racesWith WaitCommand(0.3)

        RunCommand(Launcher) {
            Launcher.speed = 0.7
        }.schedule()

        (RunCommand(TankDrivetrain) {
            TankDrivetrain.setSpeed(0.0, 0.5, 0.0)
        } racesWith WaitCommand(0.7)
        andThen pushOne()
        andThen WaitCommand(0.3)
        andThen pushOne()
        ).schedule()

    }
}