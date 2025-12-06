package org.firstinspires.ftc.teamcode.component

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit
import kotlin.math.abs

class Motor(
    private val deviceSupplier: () -> DcMotorEx?,
    var direction: Direction = Component.Direction.FORWARD,
    var max_change: Double? = 2.0,
    // private val zeroPowerBehavior: ZeroPowerBehavior,
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

    private var lasteffort = 0.0
    private var true_max_change = 0.0

    override fun reset() {
        _motor = null
    }

    override fun update(dt: Double) {
        true_max_change = (max_change ?: 0.0) * dt
    }

    fun setZPB(zpb: ZeroPowerBehavior) {
        motor.zeroPowerBehavior = zpb
    }

    var effort = 0.0
        set(value) {
            if (abs(value - field) > eps) field = value
        }

    override fun write() {
        if (abs(lasteffort - effort) > eps) {
            if (max_change == null) {
                motor.power = effort * direction.dir
                lasteffort = effort
            } else effort.coerceIn(lasteffort-true_max_change, lasteffort+true_max_change).let {
                motor.power = it
                lasteffort = it
            }

        }
    }

    val current: Double
        get() = motor.getCurrent(CurrentUnit.AMPS)

}
