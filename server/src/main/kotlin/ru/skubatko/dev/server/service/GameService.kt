package ru.skubatko.dev.server.service

import ru.skubatko.dev.api.models.GameStatusMessageTO
import org.springframework.stereotype.Service

@Service
class GameService {

    fun getStatus(): GameStatusMessageTO =
        GameStatusMessageTO()
}
