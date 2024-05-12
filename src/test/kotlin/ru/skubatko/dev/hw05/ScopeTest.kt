package ru.skubatko.dev.hw05

import ru.skubatko.dev.hw03.Command
import ru.skubatko.dev.hw05.Dependency.Companion.CLEAR_CURRENT_SCOPE
import ru.skubatko.dev.hw05.Dependency.Companion.CREATE_SCOPE
import ru.skubatko.dev.hw05.Dependency.Companion.CURRENT_SCOPE
import ru.skubatko.dev.hw05.Dependency.Companion.REGISTER
import ru.skubatko.dev.hw05.Dependency.Companion.SET_CURRENT_SCOPE
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("IoC в части контекста")
class ScopeTest {
    private val someDependency = Dependency("someDependency")
    private val someDependencyResolverStrategy = { _: Any -> 1 }

    @BeforeEach
    fun setUp() {
        InitCommand().execute()
        val iocScope = IoC.resolve<Scope>(CREATE_SCOPE)
        IoC.resolve<Command>(SET_CURRENT_SCOPE, iocScope).execute()
    }

    @AfterEach
    fun tearDown() {
        IoC.resolve<Command>(CLEAR_CURRENT_SCOPE).execute()
    }

    @DisplayName("должен разрешать зарегистрированную зависимость в текущем контексте")
    @Test
    fun `should resolve registered dependency in current scope`() {
        IoC.resolve<Command>(REGISTER, someDependency, someDependencyResolverStrategy).execute()

        assertThat(IoC.resolve<Int>(someDependency)).isEqualTo(1)
    }

    @DisplayName("должен выбрасывать исключение на незарегистрированную зависимость в текущем контексте")
    @Test
    fun `should throw exception on unregistered dependency in current scope`() {
        assertThatThrownBy { IoC.resolve<Int>(someDependency) }
    }

    @DisplayName("должен использовать родительский контекст если зависимость не определена в текущем контексте")
    @Test
    fun `should use parent scope if resolving dependency is not defined in current scope`() {
        IoC.resolve<Command>(REGISTER, someDependency, someDependencyResolverStrategy).execute()

        val iocScope = IoC.resolve<Scope>(CREATE_SCOPE)
        IoC.resolve<Command>(SET_CURRENT_SCOPE, iocScope).execute();

        assertThat(IoC.resolve<Scope>(CURRENT_SCOPE)).isEqualTo(iocScope)
        assertThat(IoC.resolve<Int>(someDependency)).isEqualTo(1)
    }

    @DisplayName("должен ожидаемо устанавливать родительский контекст для создаваемого контекста")
    @Test
    fun `should expectedly set parent scope for creating scope`() {
        val scope1 = IoC.resolve<Scope>(CREATE_SCOPE)
        val scope2 = IoC.resolve<Scope>(CREATE_SCOPE, scope1)

        IoC.resolve<Command>(SET_CURRENT_SCOPE, scope1).execute();
        IoC.resolve<Command>(REGISTER, someDependency, { _: Any -> 2 }).execute()
        IoC.resolve<Command>(SET_CURRENT_SCOPE, scope2).execute();

        assertThat(IoC.resolve<Int>(someDependency)).isEqualTo(2)
    }
}
