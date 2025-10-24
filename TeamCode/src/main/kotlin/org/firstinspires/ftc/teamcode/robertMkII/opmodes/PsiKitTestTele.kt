package org.firstinspires.ftc.teamcode.robertMkII.opmodes
/*
import com.qualcomm.hardware.lynx.LynxModule
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry
import org.firstinspires.ftc.teamcode.robertMkII.hardware.NgGamepad
import org.firstinspires.ftc.teamcode.robertMkII.subsystems.Drivetrain
import org.psilynx.psikit.core.Logger
import org.psilynx.psikit.core.rlog.RLOGServer
import org.psilynx.psikit.core.rlog.RLOGWriter
import org.psilynx.psikit.ftc.PsiKitOpMode

@TeleOp(name = "main!")
class Teleop : PsiKitOpMode() {

    override fun runOpMode() {
        psiKitSetup()

        val server = RLOGServer()
        val writer = RLOGWriter("logs.rlog")
        server.start()
        writer.start()
        Logger.addDataReceiver(server)
        Logger.addDataReceiver(writer)
        Logger.recordMetadata("test", "test")
        Logger.start()

        Logger.periodicAfterUser(0.0, 0.0)
        Logger.periodicBeforeUser()
        processHardwareInputs()

        val driver = NgGamepad(gamepad1)
        val operator = NgGamepad(gamepad2)

        val drivetrain = Drivetrain(hardwareMap)

        Logger.periodicAfterUser(0.0,0.0)

        waitForStart()
        while (!psiKitIsStopRequested) {
            val beforeUserStart = Logger.getRealTimestamp()
            Logger.periodicBeforeUser()
            val beforeUserEnd = Logger.getRealTimestamp()
            processHardwareInputs()

            driver.update()
            operator.update()

            drivetrain.setSpeed(
                -driver.left_stick_x,
                -driver.left_stick_y,
                -driver.right_stick_x
            )


            drivetrain.write()

            Logger.recordOutput("Test", 2.0)

            val afterUserStart = Logger.getRealTimestamp()
            Logger.periodicAfterUser(
                afterUserStart - beforeUserEnd,
                beforeUserEnd - beforeUserStart
            )
        }
        Logger.end()
    }

}
 */