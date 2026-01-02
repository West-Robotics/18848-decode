package org.firstinspires.ftc.teamcode.subsystems

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import org.firstinspires.ftc.teamcode.command.internal.CommandScheduler

object Telemetry {
    lateinit var telemetry: MultipleTelemetry

    var data = ArrayList<Pair<String, () -> Any>>()
    var lines = ArrayList<() -> String>()
    var show_commands = false

    fun init(telemetry: org.firstinspires.ftc.robotcore.external.Telemetry) {
        this.telemetry = MultipleTelemetry(
            telemetry,
            FtcDashboard.getInstance().telemetry
        )
    }

    fun addFunction(label: String, func: () -> Any) = data.add(Pair(label, func))
    fun addLine(line: () -> String) = lines.add(line)
    fun addLines(vararg lines: String) = lines.forEach { it.add() }
    fun addAll(builder: Telemetry.() -> Unit) {
        this.builder()
    }

    infix fun String.ids(func: () -> Any) {
        addFunction(this, func)
    }
    fun String.add() = addLine { this }

    fun update() {
        if (show_commands) {
            telemetry.addLine("------------- Commands! -------------")
            CommandScheduler.commands.forEach { telemetry.addLine(it.name()) }
            telemetry.addLine("-------------------------------------")
        }
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
        show_commands = false
    }


}
