package ru.skubatko.dev.user.client

import ru.skubatko.dev.common.rest.RestTemplateFactory
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import ru.sokomishalov.commons.core.serialization.OBJECT_MAPPER

@AutoConfiguration
@EnableConfigurationProperties(UserClientProps::class)
class UserClientAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    fun objectMapper() = OBJECT_MAPPER

    @Bean
    fun userClient(
        restTemplateBuilder: RestTemplateBuilder,
        userClientProps: UserClientProps
    ) = UserClient(RestTemplateFactory.create(restTemplateBuilder, userClientProps))
}
