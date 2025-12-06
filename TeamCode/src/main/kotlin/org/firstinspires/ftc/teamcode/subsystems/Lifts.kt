package org.firstinspires.ftc.teamcode.subsystems

import org.firstinspires.ftc.teamcode.subsystems.internal.Subsystem
import org.firstinspires.ftc.teamcode.component.Servo
import org.firstinspires.ftc.teamcode.component.Servo.ModelPWM.*
import org.firstinspires.ftc.teamcode.hardware.HardwareMap


object Lifts : Subsystem<Lifts>() {
    private val LIFT_MOVE_TImayraME: Double = 0.3
    private val lifts: List<Servo> = listOf(
        HardwareMap.lift1(AXON_MINI),
        HardwareMap.lift2(AXON_MINI),
        HardwareMap.lift3(AXON_MINI),
    )
    override val components = lifts

    enum class LiftPos(val pos: Double) {
        LOW(0.0),
        HIGH(0.12),
    }

    fun raise(lift: Int) = setPos(lift, LiftPos.HIGH)
    fun lower(lift: Int) = setPos(lift, LiftPos.LOW)

    fun setPos(lift: Int, pos: LiftPos) = setPos(lift, pos.pos)
    fun setPos(lift: Int, pos: Double) = runOnce {
        lifts[lift - 1].position = pos
    }

    fun resetLifts(pos: LiftPos = LiftPos.LOW) = resetLifts(pos.pos)
    fun resetLifts(pos: Double) = runOnce {
        lifts.forEach { it.position = pos }
    }

    fun showPos() {
        Telemetry.addAll {
            "lift 1" ids lifts[0]::position
            "lift 2" ids lifts[1]::position
            "lift 3" ids lifts[2]::position
        }
    }

}
