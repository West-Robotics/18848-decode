package org.firstinspires.ftc.teamcode.robertMkII.subsystems

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.teamcode.robertMkII.hardware.HardwareMap

object TankDrivetrain {

    private val frontLeft = HardwareMap.frontLeft(DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE)
    private val frontRight = HardwareMap.frontRight(DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE)
    private val backLeft = HardwareMap.backLeft(DcMotorSimple.Direction.REVERSE, DcMotor.ZeroPowerBehavior.BRAKE)
    private val backRight = HardwareMap.backRight(DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE)

    fun setSpeed(x: Double, y:Double, turn: Double) {
        frontLeft.effort = y - x + turn
        frontRight.effort = y + x - turn
        backLeft.effort = y + x + turn
        backRight.effort = y - x - turn
    }

    fun write() {
        frontLeft.write()
        frontRight.write()
        backLeft.write()
        backRight.write()
    }

}