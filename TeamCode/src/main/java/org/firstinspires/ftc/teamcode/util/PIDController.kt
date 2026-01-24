package org.firstinspires.ftc.teamcode.util


import com.qualcomm.robotcore.util.ElapsedTime


class PIDController(
    private val k_p: Double, 
    private val k_i: Double = 0.0,
    private val k_d: Double = 0.0,
    private val max_i: Double = 1.0,
) {
    private var prev_error = 0.0
    private val e = ElapsedTime()
    private var p = 0.0
    private var i = 0.0
    private var d = 0.0

    fun calculate(error: Double): Double {
        val dt = e.seconds()
        p = k_p * error
        d = k_d * (error - prev_error) * dt
        i += k_i * error * dt
        i = i.coerceIn(-max_i, max_i)

        prev_error = error
        e.reset()
        return p + i + d
    }
}
