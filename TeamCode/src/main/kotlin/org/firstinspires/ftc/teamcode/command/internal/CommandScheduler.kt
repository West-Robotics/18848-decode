package org.firstinspires.ftc.teamcode.command.internal

object CommandScheduler {

    var commands = arrayListOf<Command>()
        internal set

    private var triggers = arrayListOf<Trigger>()

    fun reset() {
        commands = arrayListOf()
        triggers = arrayListOf()
    }

    fun addTrigger(trigger: Trigger) = triggers.add(trigger)

    fun schedule(command: Command) {
        command.requirements.forEach { subsystem ->
            commands.filter { it.requirements.contains(subsystem) }
                .forEach {
                    it.end(true)
                    commands.remove(it)
                    it.requirements.filter { !command.requirements.contains(it) }
                        .forEach { it.disable(); it.write() } // TODO: maybe remove write?
                }
        }
        command.requirements.forEach { it.enable() }
        command.initialize()
        commands.add(command)
    }

    private fun updateCommands(dt: Double) {
        var i = 0
        while (i < commands.size) {
            val command = commands[i]
            command.requirements.forEach { requirement ->
                requirement.components.forEach { it.update(dt) }
                requirement.update(dt)
            }
            command.execute()

            if (command.isFinished()) {
                command.end(false)
                commands.remove(command)
                command.requirements.forEach { it.disable() }
            } else {
                i++
            }
            command.requirements.forEach { requirement ->
                requirement.components.forEach { it.write() }
            }
        }
    }

    private fun updateTriggers() {
        triggers.forEach {
            it.update()
            if (it.isTriggered) {
                schedule(it.command)
            }
        }
    }

    fun update() {
        val deltatime = 0.0 // add later

        updateTriggers()
        updateCommands(deltatime)
    }

    fun end() {
        commands.forEach { it.end(true) }
        commands = arrayListOf()
    }

    fun end(command: Command) {
        val toRemove = commands.firstOrNull { it == command}
        if (toRemove != null) {
            toRemove.end(true)
            commands.remove(toRemove)
            command.requirements.forEach { it.disable() }
        }
    }
}