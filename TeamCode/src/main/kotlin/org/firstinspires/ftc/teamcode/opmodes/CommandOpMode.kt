package org.firstinspires.ftc.teamcode.opmodes

import com.qualcomm.hardware.lynx.LynxModule
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.command.internal.CommandScheduler
import org.firstinspires.ftc.teamcode.component.Gamepad
import org.firstinspires.ftc.teamcode.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.subsystems.Telemetry
import kotlin.collections.forEach

//@Disabled
abstract class CommandOpMode : LinearOpMode() {

    lateinit var driver: Gamepad
    lateinit var operator: Gamepad

    abstract fun initialize()

    override fun runOpMode() {
        HardwareMap.init(hardwareMap)
        Telemetry.init(telemetry)

        val looptime = ElapsedTime()

        driver = Gamepad(gamepad1)
        operator = Gamepad(gamepad2)

        val allHubs: List<LynxModule> = hardwareMap.getAll(LynxModule::class.java)
        allHubs.forEach { it.bulkCachingMode = LynxModule.BulkCachingMode.MANUAL }

        CommandScheduler.reset()
        Telemetry.reset()
        Telemetry.addFunction("looptime", looptime::seconds)

        waitForStart()
        initialize()
        while (opModeIsActive()) {
            looptime.reset()
            allHubs.forEach { it.clearBulkCache() }

            CommandScheduler.update(looptime.seconds())

            Telemetry.update()
        }
        CommandScheduler.end()
    }
}
