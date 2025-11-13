package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.hardware.HardwareMap

object Launcher {
    private val spinnerLeft = HardwareMap.spinnerLeft(DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.FLOAT)
    private val spinnerRight = HardwareMap.spinnerRight(DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.FLOAT)

    var speed: Double
        get() = spinnerLeft.effort
        set(value: Double) {
            spinnerLeft.effort = value
            spinnerRight.effort = value
        }

    fun write() {
        spinnerLeft.write()
        spinnerRight.write()
    }

    fun logCurrent(telemetry: Telemetry) {
        telemetry.addData("left spinner current", spinnerLeft.current)
        telemetry.addData("right spinner current", spinnerRight.current)
    }

}
