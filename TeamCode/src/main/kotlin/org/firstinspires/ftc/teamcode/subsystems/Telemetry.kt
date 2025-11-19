package org.firstinspires.ftc.teamcode.subsystems

import org.firstinspires.ftc.robotcore.external.Telemetry

class Telemetry {
    lateinit var telemetry: Telemetry
    fun init(telemetry: Telemetry) {
        this.telemetry = telemetry
    }
}