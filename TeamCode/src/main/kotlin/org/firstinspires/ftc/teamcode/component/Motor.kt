package org.firstinspires.ftc.teamcode.component

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit
import kotlin.math.abs

class Motor(
    private val deviceSupplier: () -> DcMotorEx?,
    var direction: Direction = Component.Direction.FORWARD,
//    private val zeroPowerBehavior: ZeroPowerBehavior,
    val eps: Double = 0.005
): Component() {
    private var _motor: DcMotorEx? = null
    private val motor: DcMotorEx get() {
        if (_motor == null) {
            _motor = deviceSupplier() ?: error(
                "tryed to access device before OpMode init"
            )
//            _motor!!.zeroPowerBehavior = zeroPowerBehavior
            _motor!!.direction = DcMotorSimple.Direction.FORWARD
            _motor!!.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
            _motor!!.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        }
        return _motor!!
    }
    private var _effort = 0.0
    private var lasteffort = 9999.0

    override fun reset() {
        _motor = null
    }

    override fun update(dt: Double) {

    }

    fun setZPB(zpb: ZeroPowerBehavior) {
        motor.zeroPowerBehavior = zpb
    }

    var effort
        get() = _effort
        set(value) = if (abs(value - _effort) > eps) {
            _effort = value
        } else Unit

    fun write() {
        if (abs(lasteffort - effort) > eps) {
            motor.power = effort * when (direction) {
                Direction.FORWARD -> 1
                Direction.REVERSE -> -1
            }
            lasteffort = effort
        }
    }

    val current: Double
        get() = motor.getCurrent(CurrentUnit.AMPS)

}