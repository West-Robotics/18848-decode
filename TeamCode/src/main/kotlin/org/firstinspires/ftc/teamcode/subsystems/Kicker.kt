package org.firstinspires.ftc.teamcode.subsystems

import org.firstinspires.ftc.teamcode.component.Component
import org.firstinspires.ftc.teamcode.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.subsystems.internal.Subsystem
import org.firstinspires.ftc.teamcode.component.Component.Direction.*
import kotlin.math.abs

object Kicker: Subsystem<Kicker>() {
    private val kicker = HardwareMap.kicker(REVERSE)
    private val sensor = HardwareMap.kicker_sensor(
        minDist = 0.0,
        maxDist = 1.0,
        zeroVoltage = 0.0,
        maxVoltage = 3.3,
    )
    override val components: List<Component> = arrayListOf(
        kicker,
    )
    enum class KickerPos(val pos: Double) {
        LOW(0.0),
        MID(0.5),
        HIGH(1.0),
    } 

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

    fun runToPos(pos: KickerPos) = runToPos(pos.pos)

    fun runToPos(pos: Double) = run {
        kicker.effort = (sensor.position - pos).coerceIn(-1.0, 1.0)
    } until {
        abs(sensor.position - pos) <= kicker.eps
    } withEnd {
        kicker.effort = 0.0
    }

    fun pushOne() = run {
        speed = 0.5
    } withEnd {
        speed = 0.0
    } withTimeout 0.3

}
