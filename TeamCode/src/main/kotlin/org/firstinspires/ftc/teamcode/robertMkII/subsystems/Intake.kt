package org.firstinspires.ftc.teamcode.robertMkII.subsystems

import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.teamcode.robertMkII.hardware.CRServo
import org.firstinspires.ftc.teamcode.robertMkII.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.robertMkII.hardware.Servo


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

    private val lowspinner = HardwareMap.lowspinner(CRServo.ModelPWM.CR_AXON_MINI, DcMotorSimple.Direction.FORWARD)
    private val highspinner = HardwareMap.highspinner(CRServo.ModelPWM.CR_AXON_MINI, DcMotorSimple.Direction.FORWARD)

    fun setPos(liftnum: Int, pos: Intake.Position) {
        if (liftnum > lifts.size - 1 || liftnum < 0) return
        lifts[liftnum].position = pos.value
    }

    fun resetLifts(pos: Intake.Position = Intake.Position.LOW) {
        lifts.forEach { it.position = pos.value }
    }

    fun setSpinSpeed(spinner: Intake.Position, effort: Double) {
        if (spinner == Intake.Position.LOW) {
            lowspinner.effort = effort
        } else if (spinner == Intake.Position.HIGH) {
            highspinner.effort = effort
        }
    }

    fun write() {
        lowspinner.write()
        highspinner.write()
        lifts.forEach { it.write() }
    }
}
