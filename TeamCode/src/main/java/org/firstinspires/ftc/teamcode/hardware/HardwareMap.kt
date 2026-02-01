package org.firstinspires.ftc.teamcode.hardware

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.ServoImplEx
import com.qualcomm.robotcore.hardware.AnalogInput
import com.qualcomm.robotcore.hardware.DigitalChannel
import org.firstinspires.ftc.teamcode.component.*
import org.firstinspires.ftc.teamcode.component.Component.Direction
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver
import kotlin.jvm.java

object HardwareMap {
    var hardwareMap: HardwareMap? = null

    val frontLeft = motor(0)
    val frontRight = motor(1)
    val backLeft = motor(2)
    val backRight = motor(3)

    val spinnerLeft = motor(4)
    val spinnerRight = motor(5)

    val lift1 = servo(5)
    val lift2 = servo(8)
    val lift3 = servo(10)

    val sensors: List<DigitalSensorConstructor> = listOf(
        digitalsensor(0),
        digitalsensor(1),
        digitalsensor(2),
        digitalsensor(3),
        digitalsensor(4),
        digitalsensor(5),
    )

    val left_light = pwmlight(4)
    val right_light = pwmlight(11)

    val intakewheel = motor(6)
    val midtakewheel = crservo(2)
    val kicker = crservo(0)
    val kicker_sensor = analogdistancesensor(0)

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

    interface PWMLightConstructor {
        operator fun invoke(): PWMLight
    }
    private fun pwmlight(port: Int) = object : PWMLightConstructor {
        override operator fun invoke() = PWMLight { hardwareMap?.get(ServoImplEx::class.java, "s$port")}
    }

    interface AnalogDistanceSensorConstructor {
        operator fun invoke(
            // dir: Direction,
            minDist: Double = 0.0,
            maxDist: Double = 1.0,
            zeroVoltage: Double = 0.0,
            maxVoltage: Double = 3.3,
        ): AnalogDistanceSensor
    }
    private fun analogdistancesensor(port: Int) = object : AnalogDistanceSensorConstructor {
        override operator fun invoke(
            minDist: Double,
            maxDist: Double,
            zeroVoltage: Double,
            maxVoltage: Double,
        ) = AnalogDistanceSensor(
            { hardwareMap?.get(AnalogInput::class.java, "a$port") },
            minDist,
            maxDist,
            zeroVoltage,
            maxVoltage,
        )
    }

    interface PinpointConstructor {
        operator fun invoke(
            // xOffset: Double = 0.0,
            // yOffset: Double = 0.0,
            // xDirection: GoBildaPinpointDriver.EncoderDirection = GoBildaPinpointDriver.EncoderDirection.FORWARD,
            // yDirection: GoBildaPinpointDriver.EncoderDirection = GoBildaPinpointDriver.EncoderDirection.FORWARD,
            // encoderResolution: GoBildaPinpointDriver.GoBildaOdometryPods = GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD,
        ): Pinpoint
    }
    private fun gobuildapinpoint(port: Int) = object : PinpointConstructor {
        override operator fun invoke(
            // xOffset: Double,
            // yOffset: Double,
            // xDirection: GoBildaPinpointDriver.EncoderDirection,
            // yDirection: GoBildaPinpointDriver.EncoderDirection,
            // encoderResolution: GoBildaPinpointDriver.GoBildaOdometryPods
        ): Pinpoint = Pinpoint(
            { hardwareMap?.get(GoBildaPinpointDriver::class.java, "i$port") },
            // xOffset,
            // yOffset,
            // xDirection,
            // yDirection,
            // encoderResolution,
        )
    }

    interface DigitalSensorConstructor {
        operator fun invoke(): DigitalSensor
    }
    private fun digitalsensor(port: Int) = object : DigitalSensorConstructor {
        override operator fun invoke(): DigitalSensor = DigitalSensor(
            { hardwareMap?.get(DigitalChannel::class.java, "d$port")}
        )
    }
}
