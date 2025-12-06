package org.firstinspires.ftc.teamcode.command

import org.firstinspires.ftc.teamcode.subsystems.*

fun PrimeBallCommand(slot: Int) = (Lifts.resetLifts() andThen 
    IntakeWheel.spin()
    parallelTo MidtakeWheel.spin()
    parallelTo (
        Lifts.raise(slot)
        andThen Kicker.runToPos(0.3)
    )).withRequirements(
        Kicker, IntakeWheel, MidtakeWheel, Lifts
    ) withEnd Lifts.resetLifts()


