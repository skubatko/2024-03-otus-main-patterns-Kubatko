package ru.skubatko.dev.hw07.commands

import ru.skubatko.dev.hw03.Command
import ru.skubatko.dev.hw07.ServerThread

class HardStopServerThreadCommand(
    private val serverThread: ServerThread
) : Command {
    override fun execute() {
        serverThread.finish()
    }
}
