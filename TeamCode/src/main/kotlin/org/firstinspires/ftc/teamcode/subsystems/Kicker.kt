package org.firstinspires.ftc.teamcode.subsystems

import org.firstinspires.ftc.teamcode.component.Component
import org.firstinspires.ftc.teamcode.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.subsystems.internal.Subsystem
import org.firstinspires.ftc.teamcode.component.Component.Direction.FORWARD

object Kicker: Subsystem() {
    private val kicker = HardwareMap.highspinner(FORWARD)
    override val components: List<Component> = arrayListOf(kicker)

    var speed: Double
        get() = kicker.effort
        set(value) {
            kicker.effort = value
        }

    override fun disable() {
        speed = 0.0
    }

}