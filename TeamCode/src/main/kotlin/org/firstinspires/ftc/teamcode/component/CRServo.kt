package org.firstinspires.ftc.teamcode.component

import com.qualcomm.robotcore.hardware.CRServoImplEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.PwmControl
import kotlin.math.abs

/**
 * CRServo wrapper with cached writes, built in servo pwm ranges, and less functions
 *
 * @param pwm servo model, used to determine pwm range
 * @param currentThresh minimum change in commanded power to necessitate a hardware write
 */
class CRServo(
    private val deviceSupplier: () -> CRServoImplEx?,
    private val pwm: ModelPWM,
    private val dir: DcMotorSimple.Direction,
    val eps: Double = 0.005,
    private var currentThresh: Double = 0.005
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
            _crservo!!.pwmRange = PwmControl.PwmRange(pwm.min, pwm.max)
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

    fun thresh(thresh: Double) {
        this.currentThresh = thresh
    }

    fun commandedPower() = crservo.power

    /**
     * Perform hardware write
     */
    fun write() { crservo.power = effort }

    init {
    }
}