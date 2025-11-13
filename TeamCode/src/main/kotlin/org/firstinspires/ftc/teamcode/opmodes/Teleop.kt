package org.firstinspires.ftc.teamcode.opmodes

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.qualcomm.hardware.lynx.LynxModule
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.component.NgGamepad
import org.firstinspires.ftc.teamcode.component.GamepadButton
import org.firstinspires.ftc.teamcode.subsystems.TankDrivetrain
import org.firstinspires.ftc.teamcode.subsystems.Intake
import org.firstinspires.ftc.teamcode.subsystems.Launcher
import com.sfdev.assembly.state.StateMachineBuilder

@Config object TeleopConf {
    @JvmField var DEBUG: Boolean = false
}

enum class IntakeState {
    STALL,
    INTAKE,
    SENDING,
}

@TeleOp(name = ";]")
class Teleop : LinearOpMode() {
    override fun runOpMode() {
        val telemetry = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)
        val driver = NgGamepad(gamepad1)
        val operator = NgGamepad(gamepad2)

        val looptime = ElapsedTime()

        val allHubs: List<LynxModule> = hardwareMap.getAll(LynxModule::class.java)
        allHubs.forEach { it.bulkCachingMode = LynxModule.BulkCachingMode.MANUAL }

        val fsm = StateMachineBuilder()
            .state(IntakeState.STALL)
            .onEnter {
                Intake.resetLifts()
                Intake.setSpinSpeed(Intake.Position.LOW, 0.0)
                Intake.setSpinSpeed(Intake.Position.HIGH, 0.0)
            }
            .transition(
                {driver.once(GamepadButton.X)},
                IntakeState.SENDING,
                { Intake.setPos(1, Intake.Position.HIGH)}
            )
            .transition(
                {driver.once(GamepadButton.Y)},
                IntakeState.SENDING,
                { Intake.setPos(2, Intake.Position.HIGH)}
            )
            .transition(
                {driver.once(GamepadButton.B)},
                IntakeState.SENDING,
                { Intake.setPos(3, Intake.Position.HIGH)}
            )
            .transition(
                {driver.once(GamepadButton.A)},
                IntakeState.INTAKE,
            )
            .state(IntakeState.SENDING)
            .onEnter {
                Intake.setSpinSpeed(Intake.Position.LOW, 1.0)
                Intake.setSpinSpeed(Intake.Position.HIGH, 1.0)
            }
            .transitionTimed(1.0, IntakeState.STALL)
            .state(IntakeState.INTAKE)
            .onEnter { Intake.setSpinSpeed(Intake.Position.LOW, 1.0)}
            .transition(
                {driver.once(GamepadButton.X)},
                IntakeState.SENDING,
                { Intake.setPos(1, Intake.Position.HIGH)}
            )
            .transition(
                {driver.once(GamepadButton.Y)},
                IntakeState.SENDING,
                { Intake.setPos(2, Intake.Position.HIGH)}
            )
            .transition(
                {driver.once(GamepadButton.B)},
                IntakeState.SENDING,
                { Intake.setPos(3, Intake.Position.HIGH)}
            )
            .transition(
                {driver.once(GamepadButton.A)},
                IntakeState.STALL
            )
            .build()

        waitForStart()
        looptime.reset()
        fsm.start()
        while (opModeIsActive()) {
            looptime.reset()

            allHubs.forEach { it.clearBulkCache() }

            driver.update()
            operator.update()

            TankDrivetrain.setSpeed(
                -driver.left_stick_x,
                -driver.left_stick_y,
                -driver.right_stick_x,
            )

            Launcher.speed = driver.right_trigger

            fsm.update()

            TankDrivetrain.write()
            Launcher.write()
            Intake.write()

            if (TeleopConf.DEBUG) {
                // log all the stuff
            }
            telemetry.addData("looptime", looptime.seconds())
            telemetry.addData("IntakeState", fsm.state)
            Launcher.logCurrent(telemetry)
            telemetry.update()
        }
    }
}
