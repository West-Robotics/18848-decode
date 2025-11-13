package org.firstinspires.ftc.teamcode.component

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit
import kotlin.math.abs

class Motor(
    private val deviceSupplier: () -> DcMotorEx?,
    private val dir: Direction,
    private val zpb: ZeroPowerBehavior,
    val eps: Double = 0.005
) {
    private var _motor: DcMotorEx? = null
    private val motor: DcMotorEx get() {
        if (_motor == null) {
            _motor = deviceSupplier() ?: error(
                "tryed to access device before OpMode init"
            )
            _motor!!.zeroPowerBehavior = zpb
            _motor!!.direction = dir
            _motor!!.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
            _motor!!.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        }
        return _motor!!
    }
    private var _effort = 0.0
    private var lasteffort = 9999.0

    var effort
        get() = _effort
        set(value) = if (abs(value - _effort) > eps) {
            _effort = value
        } else Unit

    fun write() {
        if (abs(lasteffort - effort) > eps) {
            motor.power = effort
            lasteffort = effort
        }
    }

    val current: Double
        get() = motor.getCurrent(CurrentUnit.AMPS)

}