package org.firstinspires.ftc.teamcode.component

import com.qualcomm.robotcore.hardware.DigitalChannel

class DigitalSensor(
    private val deviceSupplier: () -> DigitalChannel?,
) {
    private var _sensor: DigitalChannel? = null
    val sensor: DigitalChannel get() {
        if (_sensor == null) {
            _sensor = deviceSupplier() ?: error(
                "tryed to access device before opmode init"
            )
        }
        return _sensor!!
    }

    val state: Boolean get() = sensor.state
}
