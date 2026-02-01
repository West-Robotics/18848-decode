package org.firstinspires.ftc.teamcode.command

import org.firstinspires.ftc.teamcode.subsystems.*
import org.firstinspires.ftc.teamcode.command.internal.*

fun prime(slot: Int?) = if (slot == null) Command() else (
    Lifts.raise(slot)
    andThen (
        IntakeWheel.spin() with
        MidtakeWheel.spin()
    ) withTimeout 0.5
    andThen (MidtakeWheel.spin() withTimeout 2.0)
    withEnd Lifts.setPos(slot, Lifts.LiftPos.HOLD)
)

fun prime() = InstantCommand {
    prime(ColorSensors.slotWithBall).schedule()
}
