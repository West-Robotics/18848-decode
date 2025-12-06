package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE
import org.firstinspires.ftc.teamcode.component.Component.Direction.FORWARD
import org.firstinspires.ftc.teamcode.component.Component.Direction.REVERSE
import org.firstinspires.ftc.teamcode.component.Component
import org.firstinspires.ftc.teamcode.component.Motor
import org.firstinspires.ftc.teamcode.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.subsystems.internal.Subsystem
import kotlin.math.max
import kotlin.math.abs

object TankDrivetrain : Subsystem<TankDrivetrain>() {

    private val frontLeft = HardwareMap.frontLeft(FORWARD)
    private val frontRight = HardwareMap.frontRight(REVERSE)
    private val backLeft = HardwareMap.backLeft(FORWARD)
    private val backRight = HardwareMap.backRight(REVERSE)

    override val components: List<Component> = arrayListOf(
        frontLeft,
        frontRight,
        backLeft,
        backRight,
    )

    fun setSpeed(speed: Double) {
        setSpeed(speed, speed, speed)
    }

    fun setSpeed(x: Double, y:Double, turn: Double) {
        val denominator: Double = max(abs(y)+abs(x)+abs(turn), 1.0)
        frontLeft.effort = (y - x*0.8 + turn)/denominator
        frontRight.effort = (y + x*0.8 - turn)/denominator
        backLeft.effort = (y + x + turn)/denominator
        backRight.effort = (y - x - turn)/denominator
    }

    override fun disable() {
        // components.filter { it is Motor }.forEach { (it as Motor).effort = 0.0 }
        setSpeed(0.0)
    }

    init {
        components.filter { it is Motor }.forEach { (it as Motor).setZPB(BRAKE) }
        // Telemetry.addAll {
        //     "frontLeft" ids { frontLeft.effort }
        //     "frontRight" ids { frontRight.effort }
        //     "backLeft" ids { backLeft.effort }
        //     "backRight" ids { backRight.effort }
        // }
    }

}
