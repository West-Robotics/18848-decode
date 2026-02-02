package org.firstinspires.ftc.teamcode.command.internal

enum class OpModeState {
    OFF,
    INIT,
    ACTIVE,
}

enum class Priority(val level: Int) {
    LOW(0),
    MEDIUM(1),
    HIGH(2),
}

object CommandScheduler {

    var commands = arrayListOf<Command>()
        internal set

    var opmode_state = OpModeState.OFF
        internal set

    private var triggers = arrayListOf<Trigger>()

    fun reset() {
        commands = arrayListOf()
        triggers = arrayListOf()
        opmode_state = OpModeState.OFF
    }

    fun init() {
        reset()
        opmode_state = OpModeState.INIT
    }

    fun start() {
        opmode_state = OpModeState.ACTIVE
    }

    fun addTrigger(trigger: Trigger) = triggers.add(trigger).also { println("trigger added") }

    fun schedule(command: Command) {
        if (!command.runStates.contains(opmode_state)) {
            Trigger { command.runStates.contains(opmode_state) }.oneshot(true).onTrue(command)
            return
        }
        command.requirements.forEach { subsystem ->
            commands.filter { it.requirements.contains(subsystem) }
                .forEach {
                    if (command.priority.level < it.priority.level) return
                    it.end(true)
                    commands.remove(it)
                    it.requirements.filter { !command.requirements.contains(it) }
                        .forEach { it.disable(); it.write() } // TODO: maybe remove write?
                }
        }
        command.requirements.forEach { it.enable() }
        command.initialize()
        commands.add(command)
        println("CommandScheduler: Scheduled $command")
        return
    }

    private fun updateCommands(dt: Double) {
        var i = 0
        while (i < commands.size) {
            val command = commands[i]
            command.requirements.forEach { requirement ->
                requirement.components.forEach { it.update(dt) }
                requirement.update(dt)
            }
            command.execute(dt)

            if (command.isFinished() || !command.runStates.contains(opmode_state)) {
                command.end(false)
                commands.remove(command)
                command.requirements.forEach { it.disable() }
                println("CommandScheduler: Removed $command")
            } else {
                i++
            }
            command.requirements.forEach { it.write() }
        }
    }

    private fun updateTriggers() { // ugly ass code, should clean later but prolly wont :/
        var i = 0
        while (i < triggers.size) {
            triggers[i].let {
                it.update()
                if (it.isTriggered) {
                    it.command.schedule()
                    if (it.oneshot) triggers.remove(it)
                    else i++
                } else i++
            }
        }
    }

    fun update(dt: Double) {
        updateTriggers()
        updateCommands(dt)
    }

    fun end() {
        commands.forEach { it.end(true) }
        commands = arrayListOf()
        opmode_state = OpModeState.OFF
    }

    fun end(command: Command) {
        val toRemove = commands.firstOrNull { it == command }
        if (toRemove != null) {
            toRemove.end(true)
            commands.remove(toRemove)
            command.requirements.forEach { it.disable() }
            println("CommandScheduler: Removed $command")
        }
    }
}
