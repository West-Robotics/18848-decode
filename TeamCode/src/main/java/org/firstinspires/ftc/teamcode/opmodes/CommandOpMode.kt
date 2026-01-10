package org.firstinspires.ftc.teamcode.opmodes

import com.qualcomm.hardware.lynx.LynxModule
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.command.internal.CommandScheduler
import org.firstinspires.ftc.teamcode.component.Gamepad
import org.firstinspires.ftc.teamcode.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.subsystems.Telemetry
import org.firstinspires.ftc.teamcode.util.SelectorCommand
import org.psilynx.psikit.ftc.PsiKitLinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.psilynx.psikit.ftc.wrappers.GamepadWrapper
import org.psilynx.psikit.ftc.OpModeControls
import org.psilynx.psikit.core.rlog.RLOGServer
import org.psilynx.psikit.core.rlog.RLOGWriter
import org.psilynx.psikit.core.Logger

abstract class CommandOpMode : PsiKitLinearOpMode() {

    lateinit var driver: Gamepad
    lateinit var operator: Gamepad
    private val looptime = ElapsedTime()

    open fun onInit() {
        // SelectorCommand(gamepad1, telemetry).schedule()
    }
    abstract fun onStart()

    override fun runOpMode() {
        psiKitSetup()
        HardwareMap.init(hardwareMap)
        Telemetry.init(telemetry)
        CommandScheduler.init()

        // val allHubs = hardwareMap.getAll(LynxModule::class.java)
        // allHubs.forEach { it.bulkCachingMode = LynxModule.BulkCachingMode.MANUAL }

        driver = Gamepad(GamepadWrapper(gamepad1))
        operator = Gamepad(GamepadWrapper(gamepad2))

        Telemetry.reset()
        // Telemetry.show_commands = true
        // Telemetry.addFunction("looptime", looptime::seconds)
        
        val server = RLOGServer()
        Logger.addDataReceiver(server)
        // val writer = RLOGWriter()
        Logger.start()

        onInit()

        while (opModeInInit()) {
            Logger.periodicBeforeUser()
            processHardwareInputs()
            looptime.reset()

            CommandScheduler.update(looptime.seconds())
            Logger.periodicAfterUser(0.0, 0.0)

        }

        waitForStart()
        CommandScheduler.start()
        onStart()
        while (opModeIsActive()) {
            Logger.periodicBeforeUser()
            processHardwareInputs()
            looptime.reset()

            // allHubs.forEach { it.clearBulkCache() }
            Logger.processInputs(
                "/DriverStation/joystick1",
                driver.gamepad as GamepadWrapper
            )
            Logger.processInputs(
                "/DriverStation/joystick2",
                operator.gamepad as GamepadWrapper
            )

            CommandScheduler.update(looptime.seconds())
            Telemetry.update()
            Logger.periodicAfterUser(0.0, 0.0)
        }
        CommandScheduler.end()
        // Logger.end()
        OpModeControls.started = false
        OpModeControls.stopped = true
    }
}
