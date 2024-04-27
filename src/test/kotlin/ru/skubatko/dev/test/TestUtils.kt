package ru.skubatko.dev

import io.mockk.MockKVerificationScope
import io.mockk.verify

fun verifyNotCalled(action: MockKVerificationScope.() -> Unit) = verify(exactly = 0, verifyBlock = action)
