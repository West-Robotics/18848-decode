package org.firstinspires.ftc.teamcode.opmodes

import org.firstinspires.ftc.teamcode.command.internal.InstantCommand
import org.firstinspires.ftc.teamcode.command.internal.RunCommand
import org.firstinspires.ftc.teamcode.subsystems.Kicker
import org.firstinspires.ftc.teamcode.subsystems.Launcher
import org.firstinspires.ftc.teamcode.subsystems.TankDrivetrain

class ScrimTeleop: CommandOpMode() {
    override fun initialize() {

        RunCommand(TankDrivetrain) {
            TankDrivetrain.setSpeed(
                driver.left_stick.x.sq,
                -driver.left_stick.y.sq,
                driver.right_stick.x.sq
            )
        }.schedule()

        driver.apply {
            a.whileTrue(
                RunCommand(Kicker) {
                    Kicker.speed = 0.5
                }
            )
            b.whileTrue(
                RunCommand(Kicker) {
                    Kicker.speed = -0.5
                }
            )
            right_bumper.whileTrue(
                RunCommand(Launcher) {
                    Launcher.speed = 0.7
                }
            )
            right_trigger.apply {
                threshold = 0.01
                whileTrue(
                    RunCommand(Launcher) {
                        Launcher.speed = toDouble()
                    }
                )
            }
        }
    }
}