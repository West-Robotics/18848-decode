package org.firstinspires.ftc.teamcode.robertMkII.opmodes

// vim:foldmethod=marker
// imports! {{{
import com.qualcomm.hardware.lynx.LynxModule
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.robertMkII.hardware.NgGamepad
import org.firstinspires.ftc.teamcode.robertMkII.hardware.GamepadButton
import org.firstinspires.ftc.teamcode.robertMkII.subsystems.Drivetrain
import org.firstinspires.ftc.teamcode.robertMkII.subsystems.Intake
import org.firstinspires.ftc.teamcode.robertMkII.subsystems.Launcher
import com.sfdev.assembly.state.StateMachineBuilder
// }}}

enum class IntakeState {
    STALL,
    INTAKE,
    SENDING,
}

@TeleOp(name = "all the stuffs")
class Teleop : LinearOpMode() {
    override fun runOpMode() {
        val driver = NgGamepad(gamepad1)
        val operator = NgGamepad(gamepad2)

        val drivetrain = Drivetrain(hardwareMap)
        val launcher = Launcher(hardwareMap)
        val intake = Intake(hardwareMap)

        val looptime = ElapsedTime()

        val allHubs: List<LynxModule> = hardwareMap.getAll(LynxModule::class.java)
        for (hub in allHubs) {
            hub.bulkCachingMode = LynxModule.BulkCachingMode.MANUAL
        }

        val fsm = StateMachineBuilder()
            .state(IntakeState.STALL)
            .onEnter {
                intake.resetLifts()
                intake.setSpinSpeed(Intake.Position.LOW, 0.0)
                intake.setSpinSpeed(Intake.Position.HIGH, 0.0)
            }
            .transition(
                {driver.once(GamepadButton.X)},
                IntakeState.SENDING,
                {intake.setPos(1, Intake.Position.HIGH)}
            )
            .transition(
                {driver.once(GamepadButton.Y)},
                IntakeState.SENDING,
                {intake.setPos(2, Intake.Position.HIGH)}
            )
            .transition(
                {driver.once(GamepadButton.B)},
                IntakeState.SENDING,
                {intake.setPos(3, Intake.Position.HIGH)}
            )
            .transition(
                {driver.once(GamepadButton.A)},
                IntakeState.INTAKE,
            )
            .state(IntakeState.SENDING)
            .onEnter {
                intake.setSpinSpeed(Intake.Position.LOW, 1.0)
                intake.setSpinSpeed(Intake.Position.HIGH, 1.0)
            }
            .transitionTimed(1, IntakeState.STALL)
            .state(IntakeState.INTAKE)
            .onEnter {intake.setSpinSpeed(Intake.Position.LOW, 1.0)}
            .transition(
                {driver.once(GamepadButton.X)},
                IntakeState.SENDING,
                {intake.setPos(1, Intake.Position.HIGH)}
            )
            .transition(
                {driver.once(GamepadButton.Y)},
                IntakeState.SENDING,
                {intake.setPos(2, Intake.Position.HIGH)}
            )
            .transition(
                {driver.once(GamepadButton.B)},
                IntakeState.SENDING,
                {intake.setPos(3, Intake.Position.HIGH)}
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
            driver.update()
            operator.update()

            for (hub in allHubs) {
                hub.clearBulkCache()
            }

            drivetrain.setSpeed(
                -driver.left_stick_x,
                -driver.left_stick_y,
                -driver.right_stick_x,
            )

            launcher.speed = driver.right_trigger

            fsm.update()
        }
    }
}
