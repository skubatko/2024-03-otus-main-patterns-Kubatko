package ru.skubatko.dev.server.config

import ru.skubatko.dev.server.jwt.JwtTokenUtil
import ru.skubatko.dev.server.user.UserDetailsServiceImpl
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.messaging.support.MessageHeaderAccessor
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import ru.sokomishalov.commons.core.log.Loggable

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
class WebSocketServerConfig(
    private val jwtTokenUtil: JwtTokenUtil,
    private val userDetailsService: UserDetailsServiceImpl
) : WebSocketMessageBrokerConfigurer {

    override fun configureMessageBroker(config: MessageBrokerRegistry) {
        config.enableSimpleBroker("/game")
        config.setApplicationDestinationPrefixes("/server")
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/websocket").withSockJS()
    }

    override fun configureClientInboundChannel(registration: ChannelRegistration) {
        registration.interceptors(object : ChannelInterceptor {
            override fun preSend(message: Message<*>, channel: MessageChannel): Message<*> {
                val accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor::class.java)
                logInfo("Headers: $accessor")
                require(accessor != null)
                if (StompCommand.CONNECT == accessor.command) {
                    val authorizationHeader = accessor.getFirstNativeHeader("Authorization")!!
                    val token = authorizationHeader.substring(7)
                    val username: String = jwtTokenUtil.getUsername(token)
                    val userDetails: UserDetails = userDetailsService.loadUserByUsername(username)
                    val usernamePasswordAuthenticationToken =
                        UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                    SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
                    accessor.user = usernamePasswordAuthenticationToken
                }
                return message
            }
        })
    }

    companion object : Loggable
}
