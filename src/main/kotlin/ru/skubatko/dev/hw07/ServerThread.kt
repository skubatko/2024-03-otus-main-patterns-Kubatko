package ru.skubatko.dev.hw07

import ru.skubatko.dev.hw03.Command
import ru.skubatko.dev.hw05.Dependency
import ru.skubatko.dev.hw05.IoC
import java.util.concurrent.BlockingQueue

typealias Action = () -> Unit

class ServerThread(
    private val q: BlockingQueue<Command>
) : Thread() {
    private var behaviour: Action = { defaultBehaviour(q) }
    private var hookAfter: Action = { }
    private var canContinue = { true }

    override fun run() {
        do {
            behaviour()
        } while (canContinue() && !isInterrupted)
        hookAfter()
    }

    fun finish() {
        canContinue = { false }
    }

    fun setBehaviour(newValue: Action) {
        behaviour = newValue
    }

    fun setHookAfter(newValue: Action) {
        hookAfter = newValue
    }

    fun setCanContinue(newValue: () -> Boolean) {
        canContinue = newValue
    }

    companion object {
        private val defaultBehaviour = { q: BlockingQueue<Command> ->
            val cmd = q.take();
            try {
                cmd.execute();
            } catch (ex: Exception) {
                IoC.resolve<Command>(Dependency("Exception handler"), cmd, ex).execute();
            }
        }
    }
}
