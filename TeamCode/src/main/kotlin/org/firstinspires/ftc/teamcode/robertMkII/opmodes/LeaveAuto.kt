package org.firstinspires.ftc.teamcode.robertMkII.opmodes

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.sfdev.assembly.state.StateMachineBuilder
import org.firstinspires.ftc.teamcode.robertMkII.subsystems.Drivetrain

@Autonomous(name = "riley want it to work pls")
class LeaveAuto : LinearOpMode() {
    enum class OpStates {
        MOVEFORWARD,
        MOVELEFT,
        OFF,
    }

    override fun runOpMode() {
        val drivetrain = Drivetrain(hardwareMap)
        val fsm = StateMachineBuilder()
            .state(OpStates.MOVEFORWARD)
                .onEnter { drivetrain.setSpeed(0.0,0.5,0.0) }
                .transitionTimed(1.0, OpStates.MOVELEFT)
            .state(OpStates.MOVELEFT)
                .onEnter { drivetrain.setSpeed(0.5,0.0,0.0) }
                .transitionTimed(1.5, OpStates.OFF)
            .state(OpStates.OFF)
                .onEnter { drivetrain.setSpeed(0.0,0.0,0.0)}
            .build()
        waitForStart()
        fsm.start()
        while (opModeIsActive()) {
            fsm.update()
            drivetrain.write()
            telemetry.addData("Current State", fsm.getState())
            telemetry.update()
        }
    }

}