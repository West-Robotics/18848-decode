package org.firstinspires.ftc.teamcode.robertMkII.hardware

import android.R.attr.value
import com.qualcomm.robotcore.hardware.PwmControl
import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.hardware.ServoImplEx
import kotlin.math.abs

/**
 * Servo wrapper with cached writes, built in servo pwm ranges, and fewer functions
 */

/**
 * @param pwm servo model, used to determine pwm range
 * @param pwm servo model, used to determine pwm range
 * @param usFrame framing rate in microseconds
 */

// TODO: add direction and initial pos as part of constructor so they are explicitly stated

class Servo(
    private val deviceSupplier: () -> ServoImplEx?,
    private val pwm: ModelPWM,
    var thresh: Double = 0.002,
    private val usFrame: Double = 5000.0,
) {
    /**
     * PWM ranges for various servo models
     *
     * Axons are limited from 510-2490 to prevent accidental wraparound (may be adjusted) in the future
     */
    enum class ModelPWM(val min: Double, val max: Double) {
        AXON_MAX(510.0, 2490.0), AXON_MINI(510.0, 2490.0), AXON_MICRO(510.0, 2490.0),
        GOBILDA_TORQUE(500.0, 2500.0), GOBILDA_SPEED(500.0, 2500.0), GOBILDA_SUPER(500.0, 2500.0),
        GENERIC(500.0, 2500.0),
    }

    private var _servo: ServoImplEx? = null
    private val servo: ServoImplEx get() {
        if (_servo == null) {
            _servo = deviceSupplier() ?: error(
                "tryed to access device before OpMode init"
            )
            _servo!!.pwmRange = PwmControl.PwmRange(pwm.min, pwm.max, usFrame)
        }
        return _servo!!
    }
    private var lastPosition = 0.0

    var direction: Servo.Direction
        get() = servo.direction
        set(value: Servo.Direction) {
            servo.direction = value
        }

    private var _position = 0.0
    var position: Double
        get() = servo.position
        set(value: Double) = if (abs(value - lastPosition) > thresh) {
            _position = value
        } else Unit

    fun write() {
        if (abs(_position - lastPosition) > thresh) {
            servo.position = _position
            lastPosition = _position
        }
    }

}