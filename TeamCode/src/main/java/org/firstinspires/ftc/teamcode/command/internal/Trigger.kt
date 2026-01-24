package org.firstinspires.ftc.teamcode.command.internal

open class Trigger(
    var supplier: () -> Boolean,
    var conditionsMet: (Boolean, Boolean) -> Boolean = { value, lastValue -> value },
    var command: Command = Command(),
    var oneshot: Boolean = false,
) {
    constructor(supplier: () -> Boolean) : this(supplier, { value, lastValue -> value }, Command())
    var lastValue = supplier()
        internal set
    var value = supplier()
        internal set

    open fun update() {
        lastValue = value
        value = supplier()
    }
    infix fun oneshot(oneshot: Boolean) = this.apply { this.oneshot = oneshot }
    val isTriggered: Boolean
        get() = conditionsMet(value, lastValue)

    infix fun and(other: Trigger) = Trigger { supplier() and other.supplier() }
    infix fun or(other: Trigger) = Trigger { supplier() or other.supplier() }
    operator fun not() = Trigger { !supplier() }

    fun toBoolean() = supplier()

    fun onTrue(func: () -> Unit) = onTrue(InstantCommand(command = func))
    fun onTrue(command: Command): Trigger {
        CommandScheduler.addTrigger(
            Trigger(
                supplier,
                { value, lastValue -> value and !lastValue },
                command,
            ).oneshot(this.oneshot)
        )
        return this
    }

    fun onFalse(func: () -> Unit) = onFalse(InstantCommand(command = func))
    fun onFalse(command: Command): Trigger {
        CommandScheduler.addTrigger(
            Trigger(
                supplier,
                { value, lastValue -> !value and lastValue },
                command
            ).oneshot(this.oneshot)
        )
        return this
    }

    fun whileTrue(command: Command): Trigger {
        CommandScheduler.addTrigger(
            Trigger(
                supplier,
                { value, lastValue -> value and !lastValue },
                command racesWith WaitUntilCommand { !supplier() }
            ).oneshot(this.oneshot)
        )
        return this
    }

    fun whileFalse(command: Command): Trigger {
        CommandScheduler.addTrigger(
            Trigger(
                supplier,
                { value, lastValue -> !value and lastValue },
                command racesWith WaitUntilCommand { supplier() }
            ).oneshot(this.oneshot)
        )
        return this
    }

    fun toggleOnTrue(command: Command): Trigger {
        CommandScheduler.addTrigger(
            command.let {
                Trigger(
                    supplier,
                    { value, lastValue -> value && !lastValue },
                    InstantCommand {
                        if (CommandScheduler.commands.contains(it)) {
                            it.cancel()
                        } else {
                            it.schedule()
                        }
                    }
                ).oneshot(this.oneshot)
            }
        )
        return this
    }

    fun toggleOnFalse(command: Command): Trigger {
        CommandScheduler.addTrigger(
            command.let {
                Trigger(
                    supplier,
                    { value, lastValue -> !value && lastValue },
                    InstantCommand {
                        if (CommandScheduler.commands.contains(it)) {
                            it.cancel()
                        } else {
                            it.schedule()
                        }
                    }
                ).oneshot(this.oneshot)
            }
        )
        return this
    }
}
