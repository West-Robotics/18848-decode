package org.firstinspires.ftc.teamcode.hardware

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.ServoImplEx
import org.firstinspires.ftc.teamcode.component.CRServo
import org.firstinspires.ftc.teamcode.component.Component.Direction
import org.firstinspires.ftc.teamcode.component.Motor
import org.firstinspires.ftc.teamcode.component.Pinpoint
import org.firstinspires.ftc.teamcode.component.Servo
import org.firstinspires.ftc.teamcode.robertmkII.hardware.GoBildaPinpointDriver
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
    val highspinner = crservo(0)

    val pinpoint = gobuildapinpoint(0)



    fun init(hardwareMap: HardwareMap) { this.hardwareMap = hardwareMap }

    interface MotorConstructor {
        operator fun invoke(
            dir: Direction,
            ): Motor
    }
    private fun motor(port: Int) = object : MotorConstructor {
        override operator fun invoke(
            dir: Direction,
        ) = Motor(
            { hardwareMap?.get(DcMotorEx::class.java, "m$port") },
            dir,
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
            { hardwareMap?.get(ServoImplEx::class.java, "s$port") },
            pwm,
            thresh,
            usFrame,
        )
    }

    interface CRServoConstructor {
        operator fun invoke(
//            pwm: CRServo.ModelPWM,
            dir: Direction,
            eps: Double = 0.005,
//            currentThresh: Double = 0.005,
        ): CRServo
    }
    private fun crservo(port: Int) = object : CRServoConstructor {
        override operator fun invoke(
//            pwm: CRServo.ModelPWM,
            dir: Direction,
            eps: Double,
//            currentThresh: Double,
        ) = CRServo(
            { hardwareMap?.get(ServoImplEx::class.java, "s$port") },
//            pwm,
            dir,
            eps,
//            currentThresh,
        )
    }

    interface PinpointConstructor {
        operator fun invoke(
            xOffset: Double = 0.0,
            yOffset: Double = 0.0,
            xDirection: GoBildaPinpointDriver.EncoderDirection = GoBildaPinpointDriver.EncoderDirection.FORWARD,
            yDirection: GoBildaPinpointDriver.EncoderDirection = GoBildaPinpointDriver.EncoderDirection.FORWARD,
            encoderResolution: GoBildaPinpointDriver.GoBildaOdometryPods = GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD,
        ): Pinpoint
    }
    private fun gobuildapinpoint(port: Int) = object : PinpointConstructor {
        override operator fun invoke(
            xOffset: Double,
            yOffset: Double,
            xDirection: GoBildaPinpointDriver.EncoderDirection,
            yDirection: GoBildaPinpointDriver.EncoderDirection,
            encoderResolution: GoBildaPinpointDriver.GoBildaOdometryPods
        ): Pinpoint = Pinpoint(
            { hardwareMap?.get(GoBildaPinpointDriver::class.java, "i$port") },
            xOffset,
            yOffset,
            xDirection,
            yDirection,
            encoderResolution,
        )
    }
}