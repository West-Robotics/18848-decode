package org.firstinspires.ftc.teamcode.subsystems


import org.firstinspires.ftc.teamcode.component.PWMLight
import org.firstinspires.ftc.teamcode.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.util.Globals
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.RADIANS
import kotlin.math.abs

object Lights : Subsystem<Lights>() {
    val left_light = HardwareMap.left_light()
    val right_light = HardwareMap.right_light()
    override val components = listOf(
        left_light,
        right_light,
    )

    enum class DisplayType(val supplier: () -> PWMLight.Color) {
        NONE({
            PWMLight.Color.OFF
        }),
        ALIGNED_WITH_GOAL({
            if (abs(Drivetrain.angleTo(Globals.alliance.goal) - Drivetrain.pos.getHeading(RADIANS)) <= 0.05) {
                PWMLight.Color.GREEN
            } else {
                PWMLight.Color.RED
            }
        }),
        BALLS_HELD({
            ColorSensors.sensors.filter { it.hasBall }.size.let {
                when (it) {
                    1 -> PWMLight.Color.BLUE
                    2 -> PWMLight.Color.INDIGO
                    3 -> PWMLight.Color.VIOLET
                    else -> PWMLight.Color.ORANGE
                }
            }
        }),
    }

    fun display(left: DisplayType = DisplayType.NONE, right: DisplayType = DisplayType.NONE) = run {
        left_light.color = left.supplier()
        right_light.color = right.supplier()
    }
}
