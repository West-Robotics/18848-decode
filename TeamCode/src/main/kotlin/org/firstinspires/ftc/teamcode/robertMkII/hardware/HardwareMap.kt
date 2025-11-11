package org.firstinspires.ftc.teamcode.robertMkII.hardware

import com.qualcomm.robotcore.hardware.CRServoImplEx
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.ServoImplEx
import kotlin.jvm.java

// TODO: add actual ports in and configure robot correctly
object HardwareMap {
    var hardwareMap: HardwareMap? = null

    val frontLeft = motor(0)
    val frontRight = motor(1)
    val backLeft = motor(2)
    val backRight = motor(3)

    val spinnerLeft = motor(4)
    val spinnerRight = motor(5)

    val lift1 = servo(0)
    val lift2 = servo(2)
    val lift3 = servo(6)

    val lowspinner = crservo(1)
    val highspinner = crservo(3)



    fun init(hardwareMap: HardwareMap) { this.hardwareMap = hardwareMap }

    interface MotorConstructor {
        operator fun invoke(
            dir: DcMotorSimple.Direction,
            zpb: DcMotor.ZeroPowerBehavior
            ): Motor
    }
    private fun motor(port: Int) = object : MotorConstructor {
        override operator fun invoke(
            dir: DcMotorSimple.Direction,
            zpb: DcMotor.ZeroPowerBehavior
        ) = Motor(
            {hardwareMap?.get(DcMotorEx::class.java, "m$port")},
            dir,
            zpb
        )
    }

    interface ServoConstructor {
        operator fun invoke(
            pwm: Servo.ModelPWM,
            thresh: Double = 0.002,
            usFrame: Double = 5000.0,
        ): Servo
    }
    private fun servo(port: Int) = object : ServoConstructor {
        override operator fun invoke(
            pwm: Servo.ModelPWM,
            thresh: Double,
            usFrame: Double,
        ) = Servo(
            {hardwareMap?.get(ServoImplEx::class.java, "s$port")},
            pwm,
            thresh,
            usFrame,
        )
    }

    interface CRServoConstructor {
        operator fun invoke(
            pwm: CRServo.ModelPWM,
            dir: DcMotorSimple.Direction,
            eps: Double = 0.005,
            currentThresh: Double = 0.005,
        ): CRServo
    }
    private fun crservo(port: Int) = object : CRServoConstructor {
        override operator fun invoke(
            pwm: CRServo.ModelPWM,
            dir: DcMotorSimple.Direction,
            eps: Double,
            currentThresh: Double,
        ) = CRServo(
            {hardwareMap?.get(CRServoImplEx::class.java, "s$port")},
            pwm,
            dir,
            eps,
            currentThresh,
        )
    }
}