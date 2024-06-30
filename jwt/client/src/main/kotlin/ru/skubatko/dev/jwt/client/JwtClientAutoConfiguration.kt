package ru.skubatko.dev.jwt.client

import ru.skubatko.dev.common.rest.RestTemplateFactory
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import ru.sokomishalov.commons.core.serialization.OBJECT_MAPPER

@AutoConfiguration
@EnableConfigurationProperties(JwtClientProps::class)
class JwtClientAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    fun objectMapper() = OBJECT_MAPPER

    @Bean
    fun jwtClient(
        restTemplateBuilder: RestTemplateBuilder,
        jwtClientProps: JwtClientProps
    ) = JwtClient(RestTemplateFactory.create(restTemplateBuilder, jwtClientProps))
}
