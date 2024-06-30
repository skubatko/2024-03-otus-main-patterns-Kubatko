package ru.skubatko.dev.server.client

import ru.skubatko.dev.common.rest.RestTemplateFactory
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import ru.sokomishalov.commons.core.serialization.OBJECT_MAPPER

@AutoConfiguration
@EnableConfigurationProperties(ServerClientProps::class)
class ServerClientAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    fun objectMapper() = OBJECT_MAPPER

    @Bean
    fun serverClient(
        restTemplateBuilder: RestTemplateBuilder,
        serverClientProps: ServerClientProps
    ) = ServerClient(RestTemplateFactory.create(restTemplateBuilder, serverClientProps))
}
