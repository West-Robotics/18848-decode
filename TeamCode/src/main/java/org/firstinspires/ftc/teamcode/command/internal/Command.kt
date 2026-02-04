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

    open fun schedule() = CommandScheduler.schedule(this)
    open fun cancel() = CommandScheduler.end(this)

    infix fun withInit(function: () -> Unit) = copy(initialize = function)
    infix fun withInit(function: InstantCommand) = copy(initialize = function.command)
    infix fun withExecute(function: (Double) -> Unit) = copy(execute = function)
    infix fun withExecute(function: InstantCommand) = copy(execute = { function.command() })
    infix fun withEnd(function: (Boolean) -> Unit) = copy(end = function)
    infix fun withEnd(function: InstantCommand) = copy(end = { function.command() })
    infix fun until(function: () -> Boolean) = copy(isFinished = function)
    infix fun withName(name: String) = copy(name = { name })
    infix fun withName(name: () -> String) = copy(name = name)
    infix fun withPriority(priority: Priority) = copy(priority = priority)
    infix fun during(state: OpModeState) = copy(runStates = mutableSetOf(state))

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

    override fun toString() = name()
}
