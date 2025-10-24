package org.firstinspires.ftc.teamcode.robertMkII.subsystems

import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.robertMkII.hardware.NgCRServo
import org.firstinspires.ftc.teamcode.robertMkII.hardware.NgServo

class Intake(hardwareMap: HardwareMap) {

    enum class Position(val value: Double) {
        LOW(0.0,),
        HIGH(0.3,),
    }

    class Lift(hardwareMap: HardwareMap, name: String, pwm: NgServo.ModelPWM = NgServo.ModelPWM.AXON_MINI, startPos: Position = Position.LOW) {
        private val servo = NgServo(hardwareMap, name, pwm)
        private lateinit var lastposition: Position
        var position = startPos

        fun write() {
            if (position != lastposition) {
                lastposition = position
                servo.position = position.value
            }
        }

    }
    private val lowspinner = NgCRServo(hardwareMap, "lowspinner", NgCRServo.ModelPWM.CR_AXON_MINI, DcMotorSimple.Direction.FORWARD)
    private val highspinner = NgCRServo(hardwareMap, "highspinner", NgCRServo.ModelPWM.CR_AXON_MINI, DcMotorSimple.Direction.FORWARD)

    private val lifts: List<Lift> = listOf(Lift(hardwareMap, "lift1"), Lift(hardwareMap, "lift2"), Lift(hardwareMap, "lift3"))

    fun setPos(liftnum: Int, pos: Position) {
        lifts[liftnum].position = pos
    }

    fun resetLifts(pos: Position = Position.LOW) {
        for (lift in lifts) {
            lift.position = pos
        }
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
        for (lift in lifts) {
            lift.write()
        }
    }
}
