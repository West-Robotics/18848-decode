package org.firstinspires.ftc.teamcode.opmodes

import com.qualcomm.hardware.lynx.LynxModule
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.command.internal.CommandScheduler
import org.firstinspires.ftc.teamcode.component.Gamepad
import org.firstinspires.ftc.teamcode.hardware.HardwareMap
import kotlin.collections.forEach

//@Disabled
abstract class CommandOpMode : LinearOpMode() {

    lateinit var driver: Gamepad
    lateinit var operator: Gamepad

    abstract fun initialize()

    override fun runOpMode() {
        HardwareMap.init(hardwareMap)

        driver = Gamepad(gamepad1)
        operator = Gamepad(gamepad2)

        val allHubs: List<LynxModule> = hardwareMap.getAll(LynxModule::class.java)
        allHubs.forEach { it.bulkCachingMode = LynxModule.BulkCachingMode.MANUAL }

        CommandScheduler.reset()

        initialize()

        waitForStart()
        while (opModeIsActive()) {
            allHubs.forEach { it.clearBulkCache() }

            CommandScheduler.update()

            telemetry.update()
        }
        CommandScheduler.end()
    }

}