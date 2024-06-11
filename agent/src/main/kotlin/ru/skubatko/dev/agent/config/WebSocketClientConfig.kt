package ru.skubatko.dev.agent.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.simp.stomp.StompSession
import org.springframework.messaging.simp.stomp.StompSessionHandler
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.messaging.WebSocketStompClient
import org.springframework.web.socket.sockjs.client.SockJsClient
import org.springframework.web.socket.sockjs.client.WebSocketTransport

@Configuration
class WebSocketClientConfig(
    @Value("\${ws.server.host}") private val serverURL: String
) {

    @Bean
    fun stompSession(sessionHandler: StompSessionHandler): StompSession =
        stompClient().connectAsync(serverURL, sessionHandler).get()

    private fun stompClient() =
        WebSocketStompClient(
            SockJsClient(
                listOf(WebSocketTransport(StandardWebSocketClient()))
            )
        ).apply {
            messageConverter = MappingJackson2MessageConverter()
            taskScheduler = ThreadPoolTaskScheduler().also { it.initialize() }
        }
}
