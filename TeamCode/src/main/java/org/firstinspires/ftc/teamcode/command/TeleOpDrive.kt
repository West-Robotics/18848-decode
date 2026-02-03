package org.firstinspires.ftc.teamcode.command

import org.firstinspires.ftc.teamcode.command.internal.*
import org.firstinspires.ftc.teamcode.subsystems.*
import kotlin.math.*

class TeleOpDrive(
//    val xSupplier: () -> Double,
    val ySupplier: () -> Double,
    val turnSupplier: () -> Double,
    val max_change: Double? = null
) : Command(name = { "TeleOpDrive" }, requirements = mutableSetOf(Drivetrain)) {

    private var slow_mode = false
    private var last_x = 0.0
    private var last_y = 0.0
    private var last_turn = 0.0

    override fun execute(dt: Double) {
        if (max_change == null) {
            Drivetrain.tankDrive(
//                xSupplier(),
                ySupplier(),
                turnSupplier(),
            )
        } else {
            val true_max_change = max_change * dt * 100

            Drivetrain.tankDrive(
//                limit(xSupplier(), last_x, true_max_change).also { last_x = it },
                limit(ySupplier(), last_y, true_max_change).also { last_y = it },
                // limit(turnSupplier(), last_turn, true_max_change).also { last_turn = it },
                turnSupplier(),
            )
        }
    }

    private fun limit(new: Double, old: Double, true_max_change: Double)
        = abs(new * (if (slow_mode) 0.5 else 1.0)).coerceIn(0.0, abs(old)+true_max_change) * sign(new)

    fun slowmode() 
        = Command()
            .withInit { slow_mode = true }
            .withEnd  { slow_mode = false }
            .withName ("SlowMode!")
}
