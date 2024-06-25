package ru.skubatko.dev.agent.config

import ru.skubatko.dev.api.models.auth.AuthReqDto
import ru.skubatko.dev.api.models.auth.AuthRespDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.simp.stomp.StompHeaders
import org.springframework.messaging.simp.stomp.StompSession
import org.springframework.messaging.simp.stomp.StompSessionHandler
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.messaging.WebSocketStompClient
import org.springframework.web.socket.sockjs.client.SockJsClient
import org.springframework.web.socket.sockjs.client.WebSocketTransport
import ru.sokomishalov.commons.core.log.Loggable

@Configuration
class WebSocketClientConfig(
    @Value("\${ws.server.host}") private val serverHost: String,
    @Value("\${ws.server.login}") private val serverLogin: String,
    @Value("\${ws.server.pass}") private val serverPass: String,
) {

    @Bean
    fun stompSession(
        sessionHandler: StompSessionHandler,
        restTemplateBuilder: RestTemplateBuilder
    ): StompSession {
        val restTemplate = restTemplateBuilder.build()
        val authRequest = HttpEntity(AuthReqDto(serverLogin, serverPass))
        val authResponse = restTemplate.postForObject(
            "http://$serverHost/login",
            authRequest,
            AuthRespDto::class.java
        )
        logInfo("authResponse: $authResponse")
        val serverUrl = "ws://$serverHost/websocket"
        val authHeader = StompHeaders().apply { set(AUTHORIZATION, "Bearer ${authResponse?.token}") }
        return stompClient().connectAsync(serverUrl, null, authHeader, sessionHandler).get()
    }

    private fun stompClient() =
        WebSocketStompClient(
            SockJsClient(
                listOf(WebSocketTransport(StandardWebSocketClient()))
            )
        ).apply {
            messageConverter = MappingJackson2MessageConverter()
            taskScheduler = ThreadPoolTaskScheduler().also { it.initialize() }
        }

    companion object : Loggable
}
