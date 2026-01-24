package org.firstinspires.ftc.teamcode.component

import com.qualcomm.robotcore.hardware.PwmControl
import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.hardware.ServoImplEx
import kotlin.math.abs


class PWMLight(
    private val deviceSupplier: () -> ServoImplEx?,
): Component() {

    private var _servo: ServoImplEx? = null
    private val servo: ServoImplEx get() {
        if (_servo == null) {
            _servo = deviceSupplier() ?: error(
                "tryed to access device before OpMode init"
            )
            // _servo!!.pwmRange = PwmControl.PwmRange(pwm.min, pwm.max, usFrame)
        }
        return _servo!!
    }
    // private var lastPosition = 0.0

    var color: Color = Color.OFF
        set(value) {
            field = value
        }

    enum class Color(val pos: Double) {
        OFF(0.0),
        RED(0.277),
        ORANGE(0.333),
        YELLOW(0.388),
        SAGE(0.444),
        GREEN(0.500),
        AZURE(0.555),
        BLUE(0.611),
        INDIGO(0.666),
        VIOLET(0.722),
        WHITE(1.0)
    }

    override fun write() {
        servo.position = color.pos
    }

    override fun update(dt: Double) { }

    override fun reset() {
        _servo = null
        color = Color.OFF
    }


}
