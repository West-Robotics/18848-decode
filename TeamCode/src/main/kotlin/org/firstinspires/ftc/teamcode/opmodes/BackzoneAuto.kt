package org.firstinspires.ftc.teamcode.opmodes

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.subsystems.*
import org.firstinspires.ftc.teamcode.command.internal.*

@TeleOp(name = "full court shots")
class BackzoneTeleop : CommandOpMode() {
    override fun initialize() {
        fun pushOne() = TimedCommand(0.3, Kicker) {
            Kicker.speed = 0.5
        }

        (
            TimedCommand(0.2, TankDrivetrain) {
                TankDrivetrain.setSpeed(0.0, 0.5, 0.0)
            } andThen TimedCommand(0.1, TankDrivetrain) {
                TankDrivetrain.setSpeed(0.0, 0.0, 0.1)
            } andThen RunCommand(Launcher) {
                Launcher.speed = 1.0
            } parallelTo (
                pushOne()
                andThen TimedCommand(1.0)
                andThen pushOne()
            )
        ).schedule()
    }
}
