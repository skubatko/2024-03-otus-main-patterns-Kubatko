package ru.skubatko.dev.jwt.app

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class JwtApplicationSBTest {

    @Test
    fun contextLoads() {
    }
}
