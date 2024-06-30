@file:Suppress("MemberVisibilityCanBePrivate")

package ru.skubatko.dev.common.rest

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate
import java.nio.charset.StandardCharsets

object RestTemplateFactory {

    fun create(restTemplateBuilder: RestTemplateBuilder): RestTemplate =
        create(restTemplateBuilder, RestProps.DEFAULT)

    fun create(
        restTemplateBuilder: RestTemplateBuilder,
        restProps: RestProps,
    ): RestTemplate =
        restTemplateBuilder
            .rootUri(restProps.baseUrl)
            .setConnectTimeout(restProps.connectTimeout)
            .setReadTimeout(restProps.readTimeout)
            .build()
            .also { restTemplate ->
                restTemplate.messageConverters
                    .find { converter -> converter is StringHttpMessageConverter }
                    .apply { (this as StringHttpMessageConverter).defaultCharset = StandardCharsets.UTF_8 }
                restTemplate.messageConverters
                    .find { converter -> converter is MappingJackson2HttpMessageConverter }
                    .apply { (this as MappingJackson2HttpMessageConverter).defaultCharset = StandardCharsets.UTF_8 }
            }
}
