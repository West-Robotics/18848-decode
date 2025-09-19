package org.firstinspires.ftc.teamcode.robertMkII.hardware

import com.qualcomm.robotcore.hardware.Gamepad
import java.util.EnumMap

enum class GamepadButton {
    A,
    B,
    X,
    Y,
    LEFT_BUMPER,
    RIGHT_BUMPER,
    DPAD_UP,
    DPAD_DOWN,
    DPAD_LEFT,
    DPAD_RIGHT,
}

class Button {
    private var value: Boolean = false
    private var lastvalue: Boolean = false

    fun update(new: Boolean) {
        lastvalue = value
        value = new
    }

    val once: Boolean
        get() = (value && !lastvalue)

    val down: Boolean
        get() = (value)
}

class NgGamepad(private val gamepad: Gamepad) {

    private val buttons: EnumMap<GamepadButton, Button> = EnumMap(GamepadButton.entries.associateWith { Button() })
    var left_stick_x: Double = 0.0
    var left_stick_y: Double = 0.0
    var right_stick_x: Double = 0.0
    var right_stick_y: Double = 0.0

    fun update() {
        buttons[GamepadButton.A]!!.update(gamepad.a)
        buttons[GamepadButton.B]!!.update(gamepad.b)
        buttons[GamepadButton.X]!!.update(gamepad.x)
        buttons[GamepadButton.Y]!!.update(gamepad.y)
        buttons[GamepadButton.LEFT_BUMPER]!!.update(gamepad.left_bumper)
        buttons[GamepadButton.RIGHT_BUMPER]!!.update(gamepad.right_bumper)
        buttons[GamepadButton.DPAD_UP]!!.update(gamepad.dpad_up)
        buttons[GamepadButton.DPAD_DOWN]!!.update(gamepad.dpad_down)
        buttons[GamepadButton.DPAD_LEFT]!!.update(gamepad.dpad_left)
        buttons[GamepadButton.DPAD_RIGHT]!!.update(gamepad.dpad_right)
        left_stick_x = gamepad.left_stick_x.toDouble()
        left_stick_y = gamepad.left_stick_y.toDouble()
        right_stick_x = gamepad.right_stick_x.toDouble()
        right_stick_y = gamepad.right_stick_y.toDouble()
    }

    fun once(button: GamepadButton) = buttons[button]!!.once

    fun down(button: GamepadButton) = buttons[button]!!.down
}