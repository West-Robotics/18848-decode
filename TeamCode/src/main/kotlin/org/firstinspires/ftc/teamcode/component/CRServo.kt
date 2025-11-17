package org.firstinspires.ftc.teamcode.component

import android.R.attr.value
import com.qualcomm.robotcore.hardware.CRServoImplEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.PwmControl
import com.qualcomm.robotcore.hardware.ServoImplEx
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry
import org.firstinspires.ftc.robotcore.external.Telemetry
import java.util.Optional
import kotlin.math.abs

/**
 * CRServo wrapper with cached writes, built in servo pwm ranges, and less functions
 *
 * @param pwm servo model, used to determine pwm range
 * @param currentThresh minimum change in commanded power to necessitate a hardware write
 */

class CRServo(
    private val deviceSupplier: () -> CRServoImplEx?,
//    private val pwm: ModelPWM,
    private val dir: DcMotorSimple.Direction,
    val eps: Double = 0.005,
//    private var currentThresh: Double = 0.005
) {
    enum class ModelPWM(val min: Double, val max: Double) {
        CR_AXON_MAX(510.0, 2490.0), CR_AXON_MINI(510.0, 2490.0), CR_AXON_MICRO(510.0, 2490.0),
        CR_GOBILDA_TORQUE(900.0, 2100.0), CR_GOBILDA_SPEED(1000.0, 2000.0), CR_GOBILDA_SUPER(1000.0, 2000.0),
    }

    private var _crservo: CRServoImplEx? = null
    private val crservo: CRServoImplEx get() {
        if (_crservo == null) {
            _crservo = deviceSupplier() ?: error(
                "tryed to access a device before OpMode init"
            )
//            _crservo!!.pwmRange = PwmControl.PwmRange(pwm.min, pwm.max)
            _crservo!!.direction = dir
        }
        return _crservo!!
    }
    private var _effort = 0.0

    var effort
        get() = _effort
        set(value) = if (abs(value - _effort) > eps) {
            _effort = value
        } else Unit

//    fun thresh(thresh: Double) {
//        this.currentThresh = thresh
//    }

    fun commandedPower() = crservo.power

    /**
     * Perform hardware write
     */
    fun write() { crservo.power = effort }

    init {
    }
}

/*
class CRServo(
    private val deviceSupplier: () -> ServoImplEx?,
    private var dir: DcMotorSimple.Direction = DcMotorSimple.Direction.FORWARD,
    val eps: Double = 0.005,
) {
    private var _servo: ServoImplEx? = null
    private val servo: ServoImplEx get() {
        if (_servo == null) {
            _servo = deviceSupplier() ?: error(
                "tryed to access a device before OpMode init"
            )
        }
        return _servo!!
    }

    private var _effort = 0.0
    var effort: Double
        get() = _effort
        set(value) = if (abs(value - _effort) > eps) {
            _effort = value
        } else Unit

    private fun clamp(value: Double, min: Double, max: Double): Double {
        if (value < min) return min
        if (value > max) return max
        return value
    }

    fun write(telemetry: Telemetry? = null) {
        val dirmod = when (dir) {
            DcMotorSimple.Direction.FORWARD -> 1.0
            DcMotorSimple.Direction.REVERSE -> -1.0
        }
        val value = ((clamp(_effort, -1.0, 1.0) * dirmod) + 1) / 2
        telemetry?.addData("kicker pos", value)
        servo.position = value
    }
}
 */