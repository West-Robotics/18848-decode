package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.component.Component
import org.firstinspires.ftc.teamcode.component.Component.Direction.REVERSE
import org.firstinspires.ftc.teamcode.component.Component.Direction.FORWARD
import org.firstinspires.ftc.teamcode.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.subsystems.internal.Subsystem

object Launcher: Subsystem<Launcher>() {
    private val spinnerLeft = HardwareMap.spinnerLeft(FORWARD)
    private val spinnerRight = HardwareMap.spinnerRight(REVERSE)

    override val components: List<Component> = arrayListOf(
        spinnerLeft,
        spinnerRight
    )

    var speed: Double
        get() = spinnerLeft.effort
        set(value: Double) {
            spinnerLeft.effort = value
            spinnerRight.effort = value
        }

    override fun disable() {
        this.speed = 0.0
    }

//    fun logCurrent(telemetry: Telemetry) {
//        telemetry.addData("left spinner current", spinnerLeft.current)
//        telemetry.addData("right spinner current", spinnerRight.current)
//    }

}
