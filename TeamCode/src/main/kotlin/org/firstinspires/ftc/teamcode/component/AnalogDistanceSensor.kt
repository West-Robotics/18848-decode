package org.firstinspires.ftc.teamcode.component

import com.qualcomm.robotcore.hardware.AnalogInput

class AnalogDistanceSensor(
    private val deviceSupplier: () -> AnalogInput?,
    val minDist: Double = 0.0,
    val maxDist: Double = 1.0,
    val zeroVoltage: Double = 0.0,
    val maxVoltage: Double = 3.3,
): Component() {
    private var _hwdevice: AnalogInput? = null
    private val hwdevice: AnalogInput get() {
        if (_hwdevice == null) {
            _hwdevice = deviceSupplier() ?: error(
                "tried to access device before opmode init"
            )
        }
        return _hwdevice!!
    }

    override fun update(dt: Double) {

    }

    override fun reset() {

    }

    override fun write() {

    }

    val position get() = (
        (hwdevice.voltage - zeroVoltage)
        / (maxVoltage - zeroVoltage)
        * (maxDist - minDist)
        + minDist
    )
}
