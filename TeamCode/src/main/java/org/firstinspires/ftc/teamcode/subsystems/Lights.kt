package org.firstinspires.ftc.teamcode.subsystems


import org.firstinspires.ftc.teamcode.hardware.HardwareMap

object Lights : Subsystem<Lights>() {
    val left_light = HardwareMap.left_light()
    val right_light = HardwareMap.right_light()
    override val components = listOf(
        left_light,
        right_light,
    )

}
