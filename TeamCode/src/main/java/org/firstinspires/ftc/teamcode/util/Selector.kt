package org.firstinspires.ftc.teamcode.util

import org.firstinspires.ftc.teamcode.command.internal.*
import com.qualcomm.robotcore.hardware.Gamepad
import org.firstinspires.ftc.robotcore.external.Telemetry
import kotlin.reflect.KProperty

class Selector<T>(val name: String, val options: List<T>) {
    constructor(name: String, vararg options: T): this(name, options.asList())
    private var index = 0
    val value get() = options[index]

    operator fun getValue(thisRef: Any, property: KProperty<*>) = value

    operator fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        index = options.indexOf(value)
    }

    fun next() {
        index++
        if (index >= options.size) index = 0
    }

    fun prev() {
        index--
        if (index < 0) index = options.size - 1
    }

    init {
        allSelectors.add(this)
    }

    companion object {
        val allSelectors: MutableList<Selector<*>> = mutableListOf()
    }
}

class SelectorCommand(val gamepad: Gamepad, val telemetry: Telemetry): Command(name = { "Selectors" }, runStates = mutableSetOf(OpModeState.INIT)) {

    private var index = 0
        set(value) {
            field = when {
                value < 0 -> Selector.allSelectors.size - 1
                value >= Selector.allSelectors.size -> 0
                else -> value
            }
        }
    private var prevGamepad: Gamepad = Gamepad()
    private val current: Selector<*> get() = Selector.allSelectors[index]

    override fun execute(dt: Double) {
        if (gamepad.dpad_up && !prevGamepad.dpad_up) {
            index++
        } else if (gamepad.dpad_down && !prevGamepad.dpad_down) {
            index--
        }

        if (gamepad.dpad_right && !prevGamepad.dpad_right) {
            current.next()
        } else if (gamepad.dpad_left && !prevGamepad.dpad_left) {
            current.prev()
        }

        prevGamepad.copy(gamepad)

        telemetry.addData(current.name, current.value)
        telemetry.update()
    }

}
