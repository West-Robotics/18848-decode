package org.firstinspires.ftc.teamcode.component

import com.qualcomm.robotcore.hardware.CRServoImplEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.PwmControl
import com.qualcomm.robotcore.hardware.ServoImplEx
import org.firstinspires.ftc.teamcode.component.Component.Direction
import java.util.Optional
import kotlin.math.abs

/**
 * CRServo wrapper with cached writes, built in servo pwm ranges, and less functions
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

    var effort = 0.0
        set(value) { if (abs(value - field) > eps) {
            field = value
        }
    }

    override fun write() {
        servo.position = (effort * direction.dir + 1) / 2
    }

    override fun reset() {
        _servo = null
    }

    override fun update(dt: Double) {

    }

}
