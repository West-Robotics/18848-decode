package org.firstinspires.ftc.teamcode.command

import org.firstinspires.ftc.teamcode.subsystems.*

fun PrimeSlot(slot: Int) = (
    (
        IntakeWheel.spin() with 
        MidtakeWheel.spin() with
        Lifts.raise(slot)
    ) withTimeout 1.0
) withEnd Lifts.lower(slot) withName "prime $slot"


