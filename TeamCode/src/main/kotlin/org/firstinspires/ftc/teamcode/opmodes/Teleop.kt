package org.firstinspires.ftc.teamcode.opmodes

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import org.firstinspires.ftc.teamcode.command.internal.*
import org.firstinspires.ftc.teamcode.command.*
import org.firstinspires.ftc.teamcode.subsystems.*
import org.firstinspires.ftc.teamcode.subsystems.Zone.*

@Disabled
@TeleOp(name = "blue whale")
class FullTeleop : CommandOpMode() {
    override fun initialize() {
        fun stop_them_all() = (
            Kicker.runToPos(Kicker.KickerPos.LOW)
            parallelTo Lifts.resetLifts()
            parallelTo IntakeWheel.stop()
            parallelTo MidtakeWheel.stop()
            parallelTo Launcher.stop()
        )

        Drivetrain.run {
            // fieldCentricDrive(
            setSpeed(
                driver.left_stick.x.sq,
                -driver.left_stick.y.sq,
                driver.right_stick.x.sq,
            )
        }.schedule()

        var zone: Zone = NEAR_FRONT

        driver.apply {
            // TODO: things
            a.toggleOnTrue(
                IntakeWheel.spin()
            )
            b.toggleOnTrue(
                IntakeWheel.reverse()
            )

            x.whileTrue(
                Kicker.gyrate(0.5)
            )
            y.whileTrue(
                Kicker.gyrate(-0.5)
            )

            right_bumper.onTrue(
                LaunchBallCommand(zone)
            )
            right_trigger.apply {
                threshold = 0.01
                whileTrue(
                    Launcher.gyrate(this.toDouble())
                )
            }
            left_trigger.apply {
                threshold = 0.9
                onTrue(
                    stop_them_all()
                )
            }
        }

        operator.apply {
            // TODO: Andrei things
            x.onTrue(
                PrimeBallCommand(1)
            )
            y.onTrue(
                PrimeBallCommand(2)
            )
            b.onTrue(
                PrimeBallCommand(3)
            )
            a.toggleOnTrue(
                RunCommand {
                    zone = Drivetrain.getZone()
                }
            )
            dpad_down.onTrue(
                InstantCommand {
                    zone = BACKZONE
                }
            )
            dpad_right.onTrue(
                InstantCommand {
                    zone = NEAR_FRONT
                }
            )
            dpad_left.onTrue( // this one is wierd but i cant really thing of a better way to do it
                InstantCommand {
                    zone = FAR_FRONT
                }
            )
            right_bumper.onTrue(
                LaunchBallCommand(zone)
            )
            right_trigger.apply {
                threshold = 0.01
                whileTrue(
                    Launcher.gyrate(this.toDouble())
                )
            }
            left_trigger.apply {
                threshold = 0.9
                onTrue(
                    stop_them_all()
                )
            }
        }

        // TODO: add manual
        Telemetry.addLines(
            "Left Trigger - kill switch",
        )
    }
}
