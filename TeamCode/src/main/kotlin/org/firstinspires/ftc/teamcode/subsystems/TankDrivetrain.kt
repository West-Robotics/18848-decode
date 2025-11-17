package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE
import org.firstinspires.ftc.teamcode.component.Component.Direction.FORWARD
import org.firstinspires.ftc.teamcode.component.Component.Direction.REVERSE
import org.firstinspires.ftc.teamcode.component.Component
import org.firstinspires.ftc.teamcode.component.Motor
import org.firstinspires.ftc.teamcode.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.subsystems.internal.Subsystem

object TankDrivetrain : Subsystem<TankDrivetrain>() {

    private val frontLeft = HardwareMap.frontLeft(FORWARD)
    private val frontRight = HardwareMap.frontRight(FORWARD)
    private val backLeft = HardwareMap.backLeft(REVERSE)
    private val backRight = HardwareMap.backRight(FORWARD)

    override val components: List<Component> = arrayListOf(
        frontLeft,
        frontRight,
        backLeft,
        backRight,
    )

    fun setSpeed(x: Double, y:Double, turn: Double) {
        frontLeft.effort = y - x + turn
        frontRight.effort = y + x - turn
        backLeft.effort = y + x + turn
        backRight.effort = y - x - turn
    }

    override fun disable() {
        components.filter { it is Motor }.forEach { (it as Motor).effort = 0.0 }
    }

    init {
        components.filter { it is Motor }.forEach { (it as Motor).setZPB(BRAKE) }
    }

}