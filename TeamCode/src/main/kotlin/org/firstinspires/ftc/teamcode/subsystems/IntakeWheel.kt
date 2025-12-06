package org.firstinspires.ftc.teamcode.subsystems

import org.firstinspires.ftc.teamcode.subsystems.internal.Subsystem
import org.firstinspires.ftc.teamcode.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.component.Component
import org.firstinspires.ftc.teamcode.component.Servo
import org.firstinspires.ftc.teamcode.component.Component.Direction.*

object IntakeWheel : Subsystem<IntakeWheel>() {
    private val wheel = HardwareMap.intakewheel(FORWARD)
    override val components = listOf(wheel)

    var speed
        get() = wheel.effort
        set(value) {
            wheel.effort = value
        }

    override fun disable() {
        wheel.effort = 0.0
    }

    fun gyrate(speed: Double) = run {
        wheel.effort = speed
    } withEnd {
        wheel.effort = 0.0
    }

    fun spin()    = gyrate(1.0)
    fun stop()    = runOnce { wheel.effort = 0.0 }
    fun reverse() = gyrate(-1.0)

}
