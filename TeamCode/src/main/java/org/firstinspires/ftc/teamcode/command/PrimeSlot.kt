package org.firstinspires.ftc.teamcode.command

import org.firstinspires.ftc.teamcode.command.internal.*
import org.firstinspires.ftc.teamcode.command.internal.group.*
import org.firstinspires.ftc.teamcode.subsystems.*

fun prime(slot: Int?) = if (slot == null) Command() else (
    Lifts.raise(slot)
    then (
        IntakeWheel.spin()
        with MidtakeWheel.spin()
    ) withTimeout 0.5
    then MidtakeWheel.spin().withTimeout(2.0)
    withEnd Lifts.setPos(slot, Lifts.LiftPos.HOLD)
)

fun prime() = DeferredCommand(Lifts, MidtakeWheel, IntakeWheel) {
    prime(ColorSensors.slotWithBall)
}
