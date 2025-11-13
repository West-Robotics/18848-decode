package org.firstinspires.ftc.teamcode.opmodes

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.sfdev.assembly.state.StateMachineBuilder
import org.firstinspires.ftc.teamcode.subsystems.Intake
import org.firstinspires.ftc.teamcode.subsystems.TankDrivetrain

//state: robot has 3 balls preloaded in order left -> right. it has its back to its own goal

@Autonomous(name = "unleash the balls")
class Auto : LinearOpMode() {
    enum class AutoStates {
        FORWARD,
        LAUNCHLEFT,
        LAUNCHMID,
        LAUNCHRIGHT,
        RIGHT,
        OFF
    }
    override fun runOpMode() {
        val fsm = StateMachineBuilder()
        .state(AutoStates.FORWARD)
            .onEnter {
                TankDrivetrain.setSpeed(0.0,0.5,0.0)
            }
            .transitionTimed(1.0,AutoStates.LAUNCHLEFT, {
                TankDrivetrain.setSpeed(0.0,0.0,0.0)
            })
        .state(AutoStates.LAUNCHLEFT)
            .onEnter {
                Intake.setPos(1, Intake.Position.HIGH)
                Intake.setSpinSpeed(Intake.Position.LOW, 1.0)
                Intake.setSpinSpeed(Intake.Position.HIGH, 1.0)
            }
            .transitionTimed(1.0,AutoStates.LAUNCHMID, {
                Intake.setPos(1, Intake.Position.LOW)
                Intake.setSpinSpeed(Intake.Position.LOW, 0.0)
                Intake.setSpinSpeed(Intake.Position.HIGH, 0.0)
            })
        .state(AutoStates.LAUNCHMID)
            .onEnter {
                Intake.setPos(2, Intake.Position.HIGH)
                Intake.setSpinSpeed(Intake.Position.LOW, 1.0)
                Intake.setSpinSpeed(Intake.Position.HIGH, 1.0)
            }
            .transitionTimed(1.0,AutoStates.LAUNCHRIGHT, {
                Intake.setPos(2, Intake.Position.LOW)
                Intake.setSpinSpeed(Intake.Position.LOW, 0.0)
                Intake.setSpinSpeed(Intake.Position.HIGH, 0.0)
            })
        .state(AutoStates.LAUNCHRIGHT)
            .onEnter {
                Intake.setPos(3, Intake.Position.HIGH)
                Intake.setSpinSpeed(Intake.Position.LOW, 1.0)
                Intake.setSpinSpeed(Intake.Position.HIGH, 1.0)
            }
            .transitionTimed(0.5,AutoStates.RIGHT, {
                Intake.setPos(3, Intake.Position.LOW)
                Intake.setSpinSpeed(Intake.Position.LOW, 0.0)
                Intake.setSpinSpeed(Intake.Position.HIGH, 0.0)
            })
        .state(AutoStates.RIGHT)
            .onEnter {
                TankDrivetrain.setSpeed(0.2,0.0,0.0)
            }
            .transitionTimed(0.5,AutoStates.OFF,{
                TankDrivetrain.setSpeed(0.0,0.0,0.0)
            })
        .build()

        waitForStart()
        fsm.start()
        while (opModeIsActive()) {

            fsm.update()

            TankDrivetrain.write()
            Intake.write()

            telemetry.addData("Current State", fsm.state)
            telemetry.update()
        }
    }
}