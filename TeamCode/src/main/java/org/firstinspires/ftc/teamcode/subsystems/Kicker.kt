package org.firstinspires.ftc.teamcode.subsystems

import org.firstinspires.ftc.teamcode.component.Component
import org.firstinspires.ftc.teamcode.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.component.Component.Direction.*
import org.firstinspires.ftc.teamcode.command.internal.*
import kotlin.math.abs

object Kicker: Subsystem<Kicker>() {
    private val kicker = HardwareMap.kicker(REVERSE)
    val sensor = HardwareMap.kicker_sensor()
    override val components: List<Component> = arrayListOf(
        kicker,
        sensor,
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

    fun fullCircle(speed: Double = 0.5) = sensor.position.let { start_pos ->
        gyrate(speed) racesWith ( Wait(0.5) andThen WaitUntilCommand { abs(sensor.position - start_pos) < 0.050 })
    }

    fun pushOne(speed: Double = 0.5) = gyrate(speed) racesWith ( Wait(0.5) andThen WaitUntilCommand { sensor.position > 0.75 } )
}
