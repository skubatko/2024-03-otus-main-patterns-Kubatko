package ru.skubatko.dev.user.app

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class UserApplicationSBTest {

    @Test
    fun contextLoads() {
    }
}
