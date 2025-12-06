package org.firstinspires.ftc.teamcode.subsystems

import org.firstinspires.ftc.teamcode.subsystems.internal.Subsystem
import org.firstinspires.ftc.teamcode.component.Component.Direction.*
import org.firstinspires.ftc.teamcode.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.component.Servo.ModelPWM.*
import org.firstinspires.ftc.teamcode.component.Servo
import kotlin.math.abs

object MidtakeWheel : Subsystem<MidtakeWheel>() {
    private val spinner = HardwareMap.midtakewheel(REVERSE)
    override val components = listOf(spinner)

    var speed: Double
        get() = spinner.effort
        set(value) {
            spinner.effort = value
        }

    fun gyrate(effort: Double) = run {
        spinner.effort = effort
    } withEnd {
        spinner.effort = 0.0
    }

    fun spin()    = gyrate(1.0)
    fun stop()    = runOnce { speed = 0.0 }
    fun reverse() = gyrate(-1.0)
}
