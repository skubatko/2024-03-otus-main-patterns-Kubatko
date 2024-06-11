package ru.skubatko.dev.agent.service

import ru.skubatko.dev.api.models.GameStatusMessageTO
import org.springframework.stereotype.Service

@Service
class GameService {

    fun handleGameStatus(gameStatus: GameStatusMessageTO) {
        // отправка статуса игры для принятия решения по следующему действию
        // например, это может быть отправка в kafka
    }
}
