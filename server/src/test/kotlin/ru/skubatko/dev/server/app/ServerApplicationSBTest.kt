package ru.skubatko.dev.server.app

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class ServerApplicationSBTest {

    @Test
    fun contextLoads() {
    }
}
