package ru.skubatko.dev.agent.test.utils

import io.mockk.MockKVerificationScope
import io.mockk.verify

fun verifyOnce(action: MockKVerificationScope.() -> Unit) = io.mockk.verify(exactly = 1, verifyBlock = action)

fun verifyNotCalled(action: MockKVerificationScope.() -> Unit) = verify(exactly = 0, verifyBlock = action)
