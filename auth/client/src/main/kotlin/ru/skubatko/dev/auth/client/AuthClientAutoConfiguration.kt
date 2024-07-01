package ru.skubatko.dev.auth.client

import ru.skubatko.dev.common.rest.RestTemplateFactory
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import ru.sokomishalov.commons.core.serialization.OBJECT_MAPPER

@AutoConfiguration
@EnableConfigurationProperties(AuthClientProps::class)
class AuthClientAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    fun objectMapper() = OBJECT_MAPPER

    @Bean
    fun jwtClient(
        restTemplateBuilder: RestTemplateBuilder,
        authClientProps: AuthClientProps
    ) = AuthClient(RestTemplateFactory.create(restTemplateBuilder, authClientProps))
}
