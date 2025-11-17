package org.firstinspires.ftc.teamcode.command.internal

import org.firstinspires.ftc.teamcode.subsystems.internal.Subsystem

class StartEndCommand(
    start: () -> Unit,
    end: () -> Unit,
    vararg requirements: Subsystem<*>,
) : Command(initialize = start, end = { _ -> end() }, requirements = requirements.toMutableSet()) {
    constructor(start: Command, end: Command): this(
        start = {
            start.initialize()
            start.execute()
        },
        end = {
            end.initialize()
            end.execute()
        },
        requirements =
            (start.requirements + end.requirements).toSet().toTypedArray()
    )
}
