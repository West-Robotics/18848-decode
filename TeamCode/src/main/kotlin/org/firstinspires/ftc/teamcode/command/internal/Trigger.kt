package org.firstinspires.ftc.teamcode.command.internal

open class Trigger(
    var supplier: () -> Boolean,
    var conditionsMet: (Boolean, Boolean) -> Boolean = { value, lastValue -> value },
    var command: Command = Command()
) {
    constructor(supplier: () -> Boolean) : this(supplier, { value, lastValue -> value }, Command())
    private var lastValue = supplier()
    private var value = supplier()

    fun update() {
        lastValue = value
        value = supplier()
    }
    val isTriggered: Boolean
        get() = conditionsMet(value, lastValue)

    infix fun and(other: Trigger) = Trigger { supplier() and other.supplier() }
    infix fun or(other: Trigger) = Trigger { supplier() or other.supplier() }
    operator fun not() = Trigger { !supplier() }

    fun onTrue(command: Command): Trigger {
        CommandScheduler.addTrigger(
            Trigger(
                supplier,
                { value, lastValue -> value and !lastValue },
                command,
            )
        )
        return this
    }

    fun onFalse(command: Command): Trigger {
        CommandScheduler.addTrigger(
            Trigger(
                supplier,
                { value, lastValue -> !value and lastValue },
                command
            )
        )
        return this
    }

    fun whileTrue(command: Command): Trigger {
        CommandScheduler.addTrigger(
            Trigger(
                supplier,
                { value, lastValue -> value and !lastValue },
                command racesWith WaitUntilCommand { !supplier() }
            )
        )
        return this
    }

    fun whileFalse(command: Command): Trigger {
        CommandScheduler.addTrigger(
            Trigger(
                supplier,
                { value, lastValue -> !value and lastValue },
                command racesWith WaitUntilCommand { supplier() }
            )
        )
        return this
    }

    fun toggleOnTrue(command: Command): Trigger {
        CommandScheduler.addTrigger(
            Trigger(
                supplier,
                { value, lastValue ->
                    value
                        && !lastValue
                        && !CommandScheduler.commands.contains(command)
                },
                command until { value && !lastValue }
            )
        )
        return this
    }

    fun toggleOnFalse(command: Command): Trigger {
        CommandScheduler.addTrigger(
            Trigger(
                supplier,
                { value, lastValue ->
                    !value
                            && lastValue
                            && !CommandScheduler.commands.contains(command)
                },
                command until { !value && lastValue }
            )
        )
        return this
    }
}