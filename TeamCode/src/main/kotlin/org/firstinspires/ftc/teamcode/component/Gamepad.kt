package org.firstinspires.ftc.teamcode.component

import com.qualcomm.robotcore.hardware.Gamepad
import org.firstinspires.ftc.teamcode.command.internal.Trigger
import org.firstinspires.ftc.teamcode.command.internal.InstantCommand
import kotlin.math.pow
import kotlin.math.sign

class Gamepad(private val gamepad: Gamepad) {

    class Axis(private val supplier: () -> Double): Number() {
        override fun toByte() = supplier().toInt().toByte()
        override fun toInt() = supplier().toInt()
        override fun toShort() = supplier().toInt().toShort()
        override fun toDouble() = supplier()
        override fun toFloat() = supplier().toFloat()
        override fun toLong() = supplier().toLong()

        val sq: Double
            get() = supplier().pow(2) * supplier().sign
        val cube: Double
            get() = supplier().pow(3)
    }

    class Joystick(
        val x: Axis,
        val y: Axis,
        val button: Trigger
    ): Trigger(button.supplier)

    class GamepadTrigger(private val doubleSupplier: () -> Double, var threshold: Double)
        : Trigger({ doubleSupplier() >= threshold }) {
        constructor(doubleSupplier: () -> Double): this(doubleSupplier, 0.7)


        fun toDouble() = doubleSupplier()

        val sq
            get() = toDouble().pow(2) * toDouble().sign

        val cube
            get() = toDouble().pow(3)
    }

    val a
        get() = Trigger { gamepad.a }

    val b
        get() = Trigger { gamepad.b }

    val x
        get() = Trigger { gamepad.x }

    val y
        get() = Trigger { gamepad.y }

    val dpad_up
        get() = Trigger { gamepad.dpad_up }

    val dpad_down
        get() = Trigger { gamepad.dpad_down }

    val dpad_right
        get() = Trigger { gamepad.dpad_right }

    val dpad_left
        get() = Trigger { gamepad.dpad_left }

    val start
        get() = Trigger { gamepad.start }

    val guide
        get() = Trigger { gamepad.guide }

    val back
        get() = Trigger { gamepad.back }

    val left_bumper
        get() = Trigger { gamepad.left_bumper }

    val right_bumper
        get() = Trigger { gamepad.right_bumper }

    val left_stick = Joystick(
        Axis { gamepad.left_stick_x.toDouble() },
        Axis { gamepad.left_stick_y.toDouble() },
        Trigger { gamepad.left_stick_button }
    )

    val right_stick = Joystick(
        Axis { gamepad.right_stick_x.toDouble() },
        Axis { gamepad.right_stick_y.toDouble() },
        Trigger { gamepad.right_stick_button }
    )

    val left_trigger = GamepadTrigger { gamepad.left_trigger.toDouble() }
    val right_trigger = GamepadTrigger { gamepad.right_trigger.toDouble() }

    fun rumble(time: Double = 0.5) = InstantCommand {
        gamepad.rumble((time * 1000).toInt())
    }
}
