package org.firstinspires.ftc.teamcode.util

import org.firstinspires.ftc.teamcode.command.internal.*
import com.qualcomm.robotcore.hardware.Gamepad
import kotlin.reflect.KProperty

class Selector<T>(val name: String, val options: List<T>) {
    constructor(name: String, vararg options: T): this(name, options.asList())
    private var index = 0
    private val value get() = options[index]

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

class SelectorCommand(val gamepad: Gamepad): Command(name = { "Selectors" }, runStates = mutableSetOf(OpModeState.INIT)) {

}
