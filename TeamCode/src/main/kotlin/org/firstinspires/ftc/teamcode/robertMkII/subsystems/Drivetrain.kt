package org.firstinspires.ftc.teamcode.robertMkII.subsystems

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.robertMkII.hardware.NgMotor
import java.util.Locale

class Drivetrain(hardwareMap: HardwareMap) {

    private val frontLeft = NgMotor(hardwareMap, "frontLeft", DcMotorSimple.Direction.REVERSE, DcMotor.ZeroPowerBehavior.BRAKE)
    private val frontRight = NgMotor(hardwareMap, "frontRight", DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE)
    private val backLeft = NgMotor(hardwareMap, "backLeft", DcMotorSimple.Direction.REVERSE, DcMotor.ZeroPowerBehavior.BRAKE)
    private val backRight = NgMotor(hardwareMap, "backRight", DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE)

    fun setSpeed(x: Double, y:Double, turn: Double) {
        frontLeft.effort = y - x - turn
        frontRight.effort = y + x + turn
        backLeft.effort = y + x - turn
        backRight.effort = y - x + turn
    }

    fun write() {
        frontLeft.write()
        frontRight.write()
        backLeft.write()
        backRight.write()
    }

}