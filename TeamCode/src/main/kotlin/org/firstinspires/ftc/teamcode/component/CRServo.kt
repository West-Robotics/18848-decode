package org.firstinspires.ftc.teamcode.component

import android.R.attr.direction
import com.qualcomm.robotcore.hardware.ServoImplEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.PwmControl
import kotlin.math.abs

/**
 * Servo wrapper with cached writes, built in servo pwm ranges, and less functions
 *
 * @param pwm servo model, used to determine pwm range
 * @param currentThresh minimum change in commanded power to necessitate a hardware write
 */
class CRServo(
    private val deviceSupplier: () -> ServoImplEx?,
//    private val pwm: ModelPWM,
    var direction: Direction,
    val eps: Double = 0.005,
): Component() {
//    enum class ModelPWM(val min: Double, val max: Double) {
//        CR_AXON_MAX(510.0, 2490.0), CR_AXON_MINI(510.0, 2490.0), CR_AXON_MICRO(510.0, 2490.0),
//        CR_GOBILDA_TORQUE(900.0, 2100.0), CR_GOBILDA_SPEED(1000.0, 2000.0), CR_GOBILDA_SUPER(1000.0, 2000.0),
//    }

    private var _servo: ServoImplEx? = null
    private val servo: ServoImplEx get() {
        if (_servo == null) {
            _servo = deviceSupplier() ?: error(
                "tryed to access a device before OpMode init"
            )
//            _servo!!.pwmRange = PwmControl.PwmRange(pwm.min, pwm.max)
//            _servo!!.direction = DcMotorSimple.Direction.FORWARD
        }
        return _servo!!
    }

    private var _effort = 0.0

    var effort
        get() = _effort
        set(value) = if (abs(value - _effort) > eps) {
            _effort = value
        } else Unit

    override fun write() {
        servo.position = (effort * direction.dir + 1) / 2
    }

    override fun reset() {
        _servo = null
    }

    override fun update(dt: Double) {

    }

}