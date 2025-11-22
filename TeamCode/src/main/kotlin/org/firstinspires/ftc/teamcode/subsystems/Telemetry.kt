package org.firstinspires.ftc.teamcode.subsystems

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry

object Telemetry {
    lateinit var telemetry: MultipleTelemetry

    var data = ArrayList<Pair<String, () -> Any>>()
    var lines = ArrayList<() -> String>()

    fun init(telemetry: org.firstinspires.ftc.robotcore.external.Telemetry) {
        this.telemetry = MultipleTelemetry(
            telemetry,
            FtcDashboard.getInstance().telemetry
        )
    }

    fun addFunction(label: String, func: () -> Any) = data.add(Pair(label, func))
    fun addLine(line: () -> String) = lines.add(line)
    fun addAll(builder: Telemetry.() -> Unit) {
        this.builder()
    }

    infix fun String.ids(func: () -> Any) {
        addFunction(this, func)
    }
    fun String.add() = addLine { this }

    fun update() {
        lines.forEach {
            telemetry.addLine(it())
        }
        data.forEach {
            telemetry.addData(it.first, it.second())
        }
        telemetry.update()
    }

    fun reset() {
        data = arrayListOf()
        lines = arrayListOf()
    }


}
