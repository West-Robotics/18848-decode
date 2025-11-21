package org.firstinspires.ftc.teamcode.command.internal

import com.qualcomm.robotcore.util.ElapsedTime

class WaitCommand(private val seconds: Double) : Command(name = { "WaitCommand" }) {
    private val e = ElapsedTime()
    override fun initialize() {
        e.reset()
    }
    override fun isFinished() = e.seconds() >= seconds
}