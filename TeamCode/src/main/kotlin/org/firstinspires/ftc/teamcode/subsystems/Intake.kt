package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.teamcode.component.CRServo
import org.firstinspires.ftc.teamcode.component.Servo
import org.firstinspires.ftc.teamcode.hardware.HardwareMap


object Intake {

    enum class Position(val value: Double) {
        LOW(0.0,),
        HIGH(0.3,),
    }

    private val lifts: List<Servo> = listOf(
        HardwareMap.lift1(Servo.ModelPWM.AXON_MINI),
        HardwareMap.lift2(Servo.ModelPWM.AXON_MINI),
        HardwareMap.lift3(Servo.ModelPWM.AXON_MINI),
    )

    private val lowspinner = HardwareMap.lowspinner(DcMotorSimple.Direction.FORWARD)
    private val highspinner = HardwareMap.highspinner(DcMotorSimple.Direction.FORWARD)

    fun setPos(liftnum: Int, pos: Position) {
        if (liftnum > lifts.size - 1 || liftnum < 0) return
        lifts[liftnum].position = pos.value
    }

    fun resetLifts(pos: Position = Position.LOW) {
        lifts.forEach { it.position = pos.value }
    }

    fun setSpinSpeed(spinner: Position, effort: Double) {
        if (spinner == Position.LOW) {
            lowspinner.effort = effort
        } else if (spinner == Position.HIGH) {
            highspinner.effort = effort
        }
    }

    fun write() {
        lowspinner.write()
        highspinner.write()
        lifts.forEach { it.write() }
    }
}
