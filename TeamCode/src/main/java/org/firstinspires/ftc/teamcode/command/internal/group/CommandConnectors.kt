package org.firstinspires.ftc.teamcode.command.internal.group

import org.firstinspires.ftc.teamcode.command.internal.*


infix fun CommandGroup.and(right: Command) = this.apply { addCommand(right) }
infix fun Command.races(right: Command) = grouped(right) { RaceCommandGroup(this, right) }
infix fun Command.then(right: Command) = grouped(right) { SequentialCommandGroup(this, right) }
infix fun Command.deadlines(right: Command) = grouped(right) { DeadlineCommandGroup(this, right) }
infix fun Command.with(right: Command) = grouped(right) { ParallelCommandGroup(this, right) }

private inline fun <reified T : CommandGroup> Command.grouped(vararg with: Command, constructor: () -> T): CommandGroup =
    when (this) {
        is T if this::class == T::class -> this.addCommands(*with)
        else -> constructor()
    } as CommandGroup