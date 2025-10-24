package org.firstinspires.ftc.teamcode.robertMkII.hardware

import com.qualcomm.robotcore.hardware.HardwareMap
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

class NgServo(
    hardwareMap: HardwareMap,
    name: String,
    pwm: ModelPWM,
    var thresh: Double = 0.002,
    usFrame: Double = 5000.0,
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

    private val servo = hardwareMap.servo.get(name) as ServoImplEx
    private var lastPosition = 0.0

    init {
        servo.pwmRange = PwmControl.PwmRange(pwm.min, pwm.max, usFrame)
    }

    var direction: Servo.Direction
        get() = servo.direction
        set(value: Servo.Direction) {
            servo.direction = value
        }

    var position: Double
        get() = servo.position
        set(value: Double) = if (abs(value - lastPosition) > thresh) {
            servo.position = value
            lastPosition = value
        } else Unit

}