package ru.skubatko.dev.agent.app

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class AgentApplicationSBTest {

    @Test
    fun contextLoads() {
    }
}
