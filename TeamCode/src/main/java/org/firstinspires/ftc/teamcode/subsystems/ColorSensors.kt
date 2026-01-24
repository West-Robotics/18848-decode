package org.firstinspires.ftc.teamcode.subsystems

import org.firstinspires.ftc.teamcode.component.*
import org.firstinspires.ftc.teamcode.hardware.HardwareMap

object ColorSensors : Subsystem<ColorSensors>() {
    class ColorSensor(val purple: DigitalSensor, val green: DigitalSensor) {
        enum class Color(val hasBall: Boolean) {
            NONE(false),
            PURPLE(true),
            GREEN(true),
            WTF(false), // maybe change to false?
        }

        val color: Color get() = when {
            ( green.state &&  purple.state) -> Color.WTF
            ( green.state && !purple.state) -> Color.GREEN
            (!green.state &&  purple.state) -> Color.PURPLE
            (!green.state && !purple.state) -> Color.NONE
            else                            -> Color.NONE
        }

        val hasBall: Boolean get() = this.color.hasBall

    }

    val sensors: List<ColorSensor> = listOf(
        ColorSensor(HardwareMap.sensors[0](), HardwareMap.sensors[1]()),
        ColorSensor(HardwareMap.sensors[2](), HardwareMap.sensors[3]()),
        ColorSensor(HardwareMap.sensors[4](), HardwareMap.sensors[5]()),
    )

    val slotWithBall: Int? get() = sensors.indexOfFirst { it.hasBall }.let {
        if (it == -1) null else it + 1 
    }

    override val components = listOf<Component>()
}
