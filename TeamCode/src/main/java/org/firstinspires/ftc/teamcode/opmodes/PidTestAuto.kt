package org.firstinspires.ftc.teamcode.opmodes

import org.firstinspires.ftc.teamcode.command.internal.*
import org.firstinspires.ftc.teamcode.command.*
import org.firstinspires.ftc.teamcode.subsystems.*
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit.METER
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES



@Autonomous(name = "the next 9/11")
class PontenialExplosives : CommandOpMode() {
    override fun onInit() {
        MoveFancy(180.0, 1.0).schedule()
    }
}



