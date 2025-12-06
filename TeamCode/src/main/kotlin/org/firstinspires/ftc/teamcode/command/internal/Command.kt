package org.firstinspires.ftc.teamcode.command.internal

import android.R.attr.description
import org.firstinspires.ftc.teamcode.subsystems.internal.Subsystem
import java.lang.Thread.interrupted

open class Command(
    private var initialize: () -> Unit = { },
    private var execute: () -> Unit = { },
    private var end: (interrupted: Boolean) -> Unit = { },
    private var isFinished: () -> Boolean = { false },
    open val requirements: MutableSet<Subsystem<*>> = mutableSetOf(),
    open var name: () -> String = { "Command" },
    open var interruptable: Boolean = true,
//    open var description: () -> String = {
//        requirements.map { it::class.simpleName!! }.toString()
//    }
) {
    fun addRequirement(requirement: Subsystem<*>) {
        requirements.add(requirement)
    }

    open fun initialize() = initialize.invoke()
    open fun execute() = execute.invoke()
    open fun end(interrupted: Boolean) = end.invoke(interrupted)
    open fun isFinished() = isFinished.invoke()

    infix fun andThen(next: Command) = CommandGroup(this, next)
    infix fun withTimeout(seconds: Double) = TimedCommand(seconds, this)
    infix fun racesWith(other: Command) = Command(
        { this.initialize(); other.initialize() },
        { this.execute(); other.execute() },
        { interrupted -> this.end(interrupted); other.end(interrupted) },
        { this.isFinished() or other.isFinished() },
        requirements = (
            this.requirements.toList()
            + other.requirements.toList()
        ).toMutableSet()
    )
    infix fun parallelTo(other: Command) = ParallelCommandGroup(this, other)

    fun schedule() = CommandScheduler.schedule(this)
    fun cancel() = CommandScheduler.end(this)

    infix fun withInit(function: () -> Unit) = copy(initialize = function)
    infix fun withInit(function: InstantCommand) = copy(initialize = function.command)
    infix fun withExecute(function: () -> Unit) = copy(execute = function)
    infix fun withExecute(function: InstantCommand) = copy(execute = function.command)
    infix fun withEnd(function: (Boolean) -> Unit) = copy(end = function)
    infix fun withEnd(function: InstantCommand) = copy(end = { function.command() })
    infix fun until(function: () -> Boolean) = copy(isFinished = function)
    infix fun withName(name: String) = copy(name = { name })
    infix fun isInteruptable(interruptable: Boolean) = copy(interruptable = interruptable)

    fun withRequirements(vararg newrequirements: Subsystem<*>) = copy(
        requirements = newrequirements.toMutableSet()
    )

    fun copy(
        initialize: () -> Unit = this::initialize,
        execute: () -> Unit = this::execute,
        end: (Boolean) -> Unit = this::end,
        isFinished: () -> Boolean = this::isFinished,
        requirements: MutableSet<Subsystem<*>> = this.requirements,
        name: () -> String = this.name,
        interruptable: Boolean = this.interruptable
//        description: () -> String = this.description
    ) = Command(
        initialize = initialize,
        execute = execute,
        end = end,
        isFinished = isFinished,
        requirements = requirements,
        name = name,
//        description = description
        interruptable = interruptable,
    )

    override fun toString() = "${name()}"
}
