package org.firstinspires.ftc.teamcode.subsystems

import org.firstinspires.ftc.teamcode.command.internal.*
import org.firstinspires.ftc.teamcode.command.internal.group.*
import org.firstinspires.ftc.teamcode.component.*
import org.firstinspires.ftc.teamcode.component.Component.Direction.*
import org.firstinspires.ftc.teamcode.hardware.*
import kotlin.math.*

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
        gyrate(speed) races ( Wait(0.5) then WaitUntilCommand { abs(sensor.position - start_pos) < 0.050 })
    }

    fun pushOne(speed: Double = 0.5) = gyrate(speed) races ( Wait(0.5) then WaitUntilCommand { sensor.position > 0.75 } )
}
