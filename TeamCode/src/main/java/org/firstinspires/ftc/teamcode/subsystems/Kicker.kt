package org.firstinspires.ftc.teamcode.subsystems

import org.firstinspires.ftc.teamcode.component.Component
import org.firstinspires.ftc.teamcode.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.component.Component.Direction.*
import kotlin.math.abs

object Kicker: Subsystem<Kicker>() {
    private val kicker = HardwareMap.kicker(REVERSE)
    override val components: List<Component> = arrayListOf(
        kicker,
    )

    var speed: Double
        get() = kicker.effort
        set(value) {
            kicker.effort = value
        }

    override fun disable() {
        speed = 0.0
    }

    fun gyrate(speed: Double) = run {
        kicker.effort = speed
    } withEnd {
        kicker.effort = 0.0
    }

    fun pushOne() = run {
        speed = 0.5
    } withEnd {
        speed = 0.0
    } withTimeout 0.3

}
