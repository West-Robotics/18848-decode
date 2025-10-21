package org.firstinspires.ftc.teamcode.robertMkII.subsystems

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.robertMkII.hardware.NgMotor

class Launcher(hardwareMap: HardwareMap) {
    private val spinnerLeft = NgMotor(hardwareMap, "spinnerLeft", DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.FLOAT)
    private val spinnerRight = NgMotor(hardwareMap, "spinnerRight", DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.FLOAT)

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

}
