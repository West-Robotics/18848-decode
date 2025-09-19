package org.firstinspires.ftc.teamcode.robertMkII.opmodes

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.qualcomm.hardware.lynx.LynxModule
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.robertMkII.hardware.NgGamepad
import org.firstinspires.ftc.teamcode.robertMkII.subsystems.Drivetrain

/*
TODO:
 - lock in!
 - add subsystems for robot
 */

@TeleOp(name = "drivetrain")
class BareTeleop: LinearOpMode() {

    override fun runOpMode() {
        val telemetry = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)

        val driver = NgGamepad(gamepad1)
        val operator = NgGamepad(gamepad2)

        val drivetrain = Drivetrain(hardwareMap)

        val looptime = ElapsedTime()

        val allHubs: List<LynxModule> = hardwareMap.getAll(LynxModule::class.java)
        for (hub in allHubs) {
            hub.bulkCachingMode = LynxModule.BulkCachingMode.MANUAL
        }

        waitForStart()
        looptime.reset()
        while (opModeIsActive()) {
            driver.update()
            operator.update()

            for (hub in allHubs) {
                hub.clearBulkCache()
            }

            drivetrain.setSpeed(
                -driver.left_stick_x,
                -driver.left_stick_y,
                -driver.right_stick_x
            )


            drivetrain.write()

//            if (debug) {
//            }
            drivetrain.showPos(telemetry)
            telemetry.addData("Pinpoint Status", drivetrain.pinpoint.deviceStatus)
            telemetry.addData("Pinpoint Frequency", drivetrain.pinpoint.frequency)
            telemetry.addData("Rev Hub Frequency", 1/looptime.seconds())
            telemetry.update()
        }
    }

}


//@Disabled
//@TeleOp(name = "NGTele", group = "Mk")
//class Teleop: LinearOpMode() {
//
//
//    override fun runOpMode() {
//        val telemetry = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)
//
//        val previousGamepad1 = Gamepad()
//        val previousGamepad2 = Gamepad()
//        val currentGamepad1 = Gamepad()
//        val currentGamepad2 = Gamepad()
//
//        val drivetrain = Drivetrain(hardwareMap)
//        val extender = Extender(hardwareMap)
//        val rotator = Rotator(hardwareMap)
//        val intake = Intake(hardwareMap)
//
//        var targetExtPos = 0.0
//        var targetRotPos = 0.0
//        var manualExt = true
//        var manualRot = false
//        var rotOverride = false
//        var extOverride = false
//
//        val er = ElapsedTime()
//        val ex = ElapsedTime()
//        val looptime = ElapsedTime()
//
//        val allHubs: List<LynxModule> = hardwareMap.getAll(LynxModule::class.java)
//        for (hub in allHubs) {
//            hub.bulkCachingMode = LynxModule.BulkCachingMode.MANUAL
//        }
//
//        waitForStart()
//        intake.wristPos = Intake.HandPosition.INTAKE
//        looptime.reset()
//        while (opModeIsActive()) {
//            previousGamepad1.copy(currentGamepad1)
//            previousGamepad2.copy(currentGamepad2)
//
//            currentGamepad1.copy(gamepad1)
//            currentGamepad2.copy(gamepad2)
//
//            for (hub in allHubs) {
//                hub.clearBulkCache()
//            }
//
//            drivetrain.setSpeed(
//                -currentGamepad1.left_stick_x.toDouble(),
//                -currentGamepad1.left_stick_y.toDouble(),
//                -currentGamepad1.right_stick_x.toDouble()
//            )
//
//            //extension
//            if (currentGamepad2.b && !previousGamepad2.b) {
//                targetExtPos = if (targetExtPos > 0.0) -1.0 else topExtensionLimit.toDouble()
//                extOverride = false
//                ex.reset()
//            }
//            if (currentGamepad2.right_bumper && !previousGamepad2.right_bumper) {
//                manualExt = !manualExt
//                currentGamepad2.rumble(100)
//            }
//            if ((abs(extender.getEffort()) > 0.85 && abs(extender.getVelocity()) < 10) || ex.seconds() > extenderTimeLimit) {
//                if (extender.getEffort() >0) {
//                    topExtensionLimit = extender.getTicks()
//                } else {
//                    extender.resetEncoder(0)
//                }
//                extOverride = true
//            }
//            if (!manualExt) {
//                if (!extOverride) {
//                    extender.runToPos(targetExtPos, extender.getTicks().toDouble())
//                }
//            } else {
//                extender.setEffort(-currentGamepad2.left_stick_y.toDouble())
//                if (currentGamepad2.dpad_down && !previousGamepad2.dpad_down) {
//                    extender.resetEncoder()
//                } else if (currentGamepad2.dpad_up && !previousGamepad2.dpad_up) {
//                    extender.resetEncoder(topExtensionLimit)
//                }
//            }
//
//            //rotation
//            if (currentGamepad2.y && !previousGamepad2.y) {
//                //targetRotPos = if (targetRotPos >0.0) 0.0 else topRotationLimit.toDouble()
//                targetRotPos = if (targetRotPos >0.0) -1.0 else 1.0
//                rotOverride = false
//                er.reset()
//            }
//            if (currentGamepad2.back && !previousGamepad2.back) {
//                manualRot = !manualRot
//                rotOverride = true
//            }
//            if (er.seconds() >= rotatorTimeLimit) {
//                rotator.setEffort(0.0)
//                rotOverride = true
//            }
//            if (!manualRot) {
//                if (!rotOverride){
//                    //rotator.runToPos(targetRotPos, rotator.getTicks().toDouble())
//                    rotator.setEffort(targetRotPos)
//                }
//            } else {
//                rotator.setEffort(currentGamepad2.right_stick_y.toDouble())
//                if (currentGamepad2.dpad_left && !previousGamepad2.dpad_left) {
//                    topRotationLimit = rotator.getTicks()
//                } else if (currentGamepad2.dpad_right && !previousGamepad2.dpad_right) {
//                    rotator.resetEncoder()
//                }
//            }
//
//            //Intake
//            if (currentGamepad2.a && !previousGamepad2.a) {
//                intake.wristPos = if (intake.wristPos == Intake.HandPosition.INTAKE) Intake.HandPosition.OUTTAKE else Intake.HandPosition.INTAKE
//            }
//            if (dummyOn) {
//                intake.wristPos = Intake.HandPosition.DUMMY
//            }
//            if (intake.wristPos == Intake.HandPosition.INTAKE) {
//                intake.setSpinSpeed(-currentGamepad2.left_trigger.toDouble()+currentGamepad2.right_trigger.toDouble())
//            } else {
//                intake.setSpinSpeed(currentGamepad2.left_trigger.toDouble()-currentGamepad2.right_trigger.toDouble())
//            }
//
//
//            drivetrain.write()
//            extender.write()
//            rotator.write()
//            intake.writeSpinner()
//
//
//            if (debug) {
//                telemetry.addData("extensions", extender.getTicks())
//                telemetry.addData("rotations", rotator.getTicks())
//                //telemetry.addData("Top Extension Limit", topExtensionLimit)
//                //telemetry.addData("Top Rotation Limit", topRotationLimit)
//                telemetry.addData("Extension force", extender.getEffort())
//                telemetry.addData("Rotation force", rotator.getEffort())
//                telemetry.addData("Wrist Position", intake.wristPos)
//                telemetry.addData("Extender tickV", extender.getVelocity())
//                telemetry.addData("Rotator tickV", rotator.getVelocity())
//                telemetry.addData("Rotator targetPos", targetRotPos)
//                telemetry.addData("Extender targetPos", targetExtPos)
//                telemetry.addData("Rotator current", rotator.getCurrent())
//                telemetry.addData("Extender current", extender.getCurrent())
//                telemetry.addData("e", er.seconds())
//            }
//            telemetry.addData("Pinpoint Status", drivetrain.pinpoint.deviceStatus)
//            telemetry.addData("Pinpoint Frequency", drivetrain.pinpoint.frequency)
//            telemetry.addData("Rev Hub Frequency", 1/looptime.seconds())
//            telemetry.update()
//        }
//    }
//}
