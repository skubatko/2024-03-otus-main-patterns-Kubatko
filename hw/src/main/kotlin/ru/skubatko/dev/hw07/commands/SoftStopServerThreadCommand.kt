package ru.skubatko.dev.hw07.commands

import ru.skubatko.dev.hw03.Command
import ru.skubatko.dev.hw07.ServerThread
import java.util.concurrent.BlockingQueue

class SoftStopServerThreadCommand(
    private val serverThread: ServerThread,
    private val q: BlockingQueue<Command>
) : Command {
    override fun execute() {
        serverThread.setCanContinue { q.size > 0 }
    }
}
