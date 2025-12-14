package org.firstinspires.ftc.teamcode.opmodes

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.subsystems.*
import org.firstinspires.ftc.teamcode.subsystems.Zone.*
import org.firstinspires.ftc.teamcode.command.internal.*
import org.firstinspires.ftc.teamcode.command.*

// load two middle and one on one side (doesnt matter which)

@Autonomous(name = "christmas presents")
class BackzoneAuto : CommandOpMode() {
    override fun onInit() {
        Telemetry.show_commands = true
        LaunchPreloads(BACKZONE).schedule()
    }
}
