package org.firstinspires.ftc.teamcode.command.internal

import org.firstinspires.ftc.teamcode.subsystems.*

open class Command(
    private var initialize: () -> Unit = { },
    private var execute: (Double) -> Unit = { },
    private var end: (Boolean) -> Unit = { },
    private var isFinished: () -> Boolean = { false },
    open var requirements: MutableSet<Subsystem<*>> = mutableSetOf(),
    open var name: () -> String = { "Command" },
    open var priority: Priority = Priority.MEDIUM,
    open var runStates: MutableSet<OpModeState> = mutableSetOf(OpModeState.ACTIVE)
//    open var description: () -> String = {
//        requirements.map { it::class.simpleName!! }.toString()
//    }
) {
    fun addRequirement(requirement: Subsystem<*>) {
        requirements.add(requirement)
    }

    open fun initialize() = initialize.invoke()
    open fun execute(dt: Double) = execute.invoke(dt)
    open fun end(interrupted: Boolean) = end.invoke(interrupted)
    open fun isFinished() = isFinished.invoke()

    infix fun andThen(next: Command) = CommandGroup(this, next)
    infix fun withTimeout(seconds: Double) = TimedCommand(seconds, this)
    infix fun races(other: Command) = Command(
        { this.initialize(); other.initialize() },
        { dt -> this.execute(dt); other.execute(dt) },
        { interrupted -> this.end(interrupted); other.end(interrupted) },
        { this.isFinished() or other.isFinished() },
        requirements = (
            this.requirements.toList()
            + other.requirements.toList()
        ).toMutableSet()
    )
    infix fun racesWith(other: Command) = races(other)
    infix fun with(other: Command) = ParallelCommandGroup(this, other)
    infix fun parallelTo(other: Command) = ParallelCommandGroup(this, other)

    fun schedule() = CommandScheduler.schedule(this)
    fun cancel() = CommandScheduler.end(this)

    infix fun withInit(function: () -> Unit) = this.apply { initialize = function}
    infix fun withInit(function: InstantCommand) = this.apply { initialize = function.command }
    infix fun withExecute(function: (Double) -> Unit) = this.apply { execute = function }
    infix fun withExecute(function: InstantCommand) = this.apply { execute = { function.command() } }
    infix fun withEnd(function: (Boolean) -> Unit) = this.apply { end = function }
    infix fun withEnd(function: InstantCommand) = this.apply { end = { function.command() } }
    infix fun until(function: () -> Boolean) = this.apply { isFinished = function }
    infix fun withName(name: String) = this.apply { this.name = { name } }
    infix fun withName(name: () -> String) = this.apply { this.name = name }
    infix fun withPriority(priority: Priority) = this.apply { this.priority = priority }
    infix fun during(state: OpModeState) = this.apply { runStates = mutableSetOf(state) }

    fun during(vararg newrunStates: OpModeState) = copy(
        runStates = newrunStates.toMutableSet()
    )

    fun withRequirements(vararg newrequirements: Subsystem<*>) = copy(
        requirements = newrequirements.toMutableSet()
    )

    fun copy(
        initialize: () -> Unit = this::initialize,
        execute: (Double) -> Unit = this::execute,
        end: (Boolean) -> Unit = this::end,
        isFinished: () -> Boolean = this::isFinished,
        requirements: MutableSet<Subsystem<*>> = this.requirements,
        name: () -> String = this.name,
        priority: Priority = this.priority,
        runStates: MutableSet<OpModeState> = this.runStates,
//        description: () -> String = this.description
    ) = Command(
        initialize = initialize,
        execute = execute,
        end = end,
        isFinished = isFinished,
        requirements = requirements,
        name = name,
        priority = priority,
        runStates = runStates,
//        description = description
    )

    override fun toString() = "${name()}"
}
