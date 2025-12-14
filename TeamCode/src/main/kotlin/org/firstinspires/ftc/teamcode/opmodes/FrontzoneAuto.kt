package org.firstinspires.ftc.teamcode.opmodes


import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.command.internal.*
import org.firstinspires.ftc.teamcode.command.*
import org.firstinspires.ftc.teamcode.subsystems.*
import org.firstinspires.ftc.teamcode.subsystems.Zone.*

// same loadout as others; launcher facing the goal, two in center one on sides

@Autonomous(name = "lame alliance partners")
class FrontZoneAuto: CommandOpMode() {
    override fun onStart() {
        (
            Drivetrain.fixedSpeed(0.0, 0.4, 0.0) withTimeout 1.0
            andThen LaunchPreloads(FAR_FRONT)

        )
    }
}
