package org.firstinspires.ftc.teamcode.command

import org.firstinspires.ftc.teamcode.command.internal.*
import org.firstinspires.ftc.teamcode.command.internal.group.*
import org.firstinspires.ftc.teamcode.subsystems.*
import org.firstinspires.ftc.teamcode.util.*
import kotlin.math.*

fun ShootingState(): Command {
    return (
//            IntakeWheel.spin()
//            with MidtakeWheel.spin()
//            with Launcher.run {
//                spinFromDistance(Drivetrain.distanceTo(Globals.alliance.goal))
//            }
            ParallelCommandGroup(
                IntakeWheel.spin(),
                MidtakeWheel.spin(),
                Launcher.run {
                    spinFromDistance(Drivetrain.distanceTo((Globals.alliance.goal)))
                }
            )
        ) races (
            TurnCommand(Drivetrain.angleTo(Globals.alliance.goal) + PI)
            then DeferredCommand(Lifts, Kicker) {
                val left = ColorSensors.sensors[0].hasBall
                val middle = ColorSensors.sensors[1].hasBall
                val right = ColorSensors.sensors[2].hasBall

                val out = SequentialCommandGroup()

                if (left && right) {
                    out.addCommands(
                        Lifts.raise(1),
                        Wait(1.5),
                        Lifts.raise(3),
                        Wait(1.0),
                        ParallelCommandGroup(Kicker.pushOne(), Wait(1.5)),
                        ParallelCommandGroup(Kicker.pushOne(), Wait(0.5)),
                    )
                    if (middle) {
                        out.addCommands(Lifts.raise(2), Wait(1.0), Kicker.pushOne())
                    }
                } else if ((left || right) && middle) {
                    val side = if (left) 1 else 3
                    out.addCommands(
                        Lifts.raise(side),
                        Wait(0.5),
                        Lifts.raise(2),
                        Wait(1.0),
                        ParallelCommandGroup(Kicker.pushOne(), Wait(1.0)),
                        Kicker.pushOne(),
                    )
                } else if (left || right) {
                    val side = if (left) 1 else 3
                    out.addCommands(
                        Lifts.raise(side),
                        Wait(2.5),
                        Kicker.pushOne(),
                    )
                } else if (middle) {
                    out.addCommands(Lifts.raise(2), Wait(1.0), Kicker.pushOne())
                }
                out
            }
        ) withEnd {
            Lifts.resetLifts(Lifts.LiftPos.HOLD).run()
            Launcher.speed = 0.0
            IntakeWheel.speed = 0.0
            MidtakeWheel.speed = 0.0
        }
}