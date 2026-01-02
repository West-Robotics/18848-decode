package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.component.Component
import org.firstinspires.ftc.teamcode.component.Component.Direction.REVERSE
import org.firstinspires.ftc.teamcode.component.Component.Direction.FORWARD
import org.firstinspires.ftc.teamcode.hardware.HardwareMap


enum class Zone(val launcher_speed: Double) {
    BACKZONE(1.0),
    FAR_FRONT(0.8),
    NEAR_FRONT(0.7)
}


object Launcher: Subsystem<Launcher>() {

    private val spinnerLeft = HardwareMap.spinnerLeft(REVERSE)
    private val spinnerRight = HardwareMap.spinnerRight(FORWARD)

    override val components: List<Component> = arrayListOf(
        spinnerLeft,
        spinnerRight
    )

    var speed: Double
        get() = spinnerLeft.effort
        set(value: Double) {
            spinnerLeft.effort = value
            spinnerRight.effort = value
        }

    fun gyrate(speed: Double) = run {
        this.speed = speed
    } withEnd {
        this.speed = 0.0
    }

    fun intake() = gyrate(-0.7)

    fun spin(zone: Zone) = gyrate(zone.launcher_speed)
    fun stop() = runOnce { this.speed = 0.0 }

    fun spinFromDistance(dist: Double) {
        // speed = 
    }

    override fun disable() {
        this.speed = 0.0
    }
}
