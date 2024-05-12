package ru.skubatko.dev.hw05

import ru.skubatko.dev.hw03.Command
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("IoC")
class ScopeTest {

    @BeforeEach
    fun setUp() {
        InitCommand().execute()
        val iocScope = IoC.resolve<Scope>(Dependency("IoC.Scope.Create"))
        IoC.resolve<Command>(Dependency("IoC.Scope.Current.Set"), iocScope).execute()
    }

    @AfterEach
    fun tearDown() {
        IoC.resolve<Command>(Dependency("IoC.Scope.Current.Clear")).execute()
    }

    @DisplayName("должен разрешать зарегистрированную зависимость в текущем контексте")
    @Test
    fun `should resolve registered dependency in current scope`() {
        IoC.resolve<Command>(Dependency("IoC.Register"), Dependency("someDependency"), { _: Any -> 1 }).execute()

        assertThat(IoC.resolve<Int>(Dependency("someDependency"))).isEqualTo(1)
    }

    @DisplayName("должен выбрасывать исключение на незарегистрированную зависимость в текущем контексте")
    @Test
    fun `should throw exception on unregistered dependency in current scope`() {
        assertThatThrownBy { IoC.resolve<Int>(Dependency("someDependency")) }
    }

    @DisplayName("должен использовать родительский контекст если зависимость не определена в текущем контексте")
    @Test
    fun `should use parent scope if resolving dependency is not defined in current scope`() {
        IoC.resolve<Command>(Dependency("IoC.Register"), Dependency("someDependency"), { _: Any -> 1 }).execute()

        val iocScope = IoC.resolve<Scope>(Dependency("IoC.Scope.Create"))
        IoC.resolve<Command>(Dependency("IoC.Scope.Current.Set"), iocScope).execute();

        assertThat(IoC.resolve<Scope>(Dependency("IoC.Scope.Current"))).isEqualTo(iocScope)
        assertThat(IoC.resolve<Int>(Dependency("someDependency"))).isEqualTo(1)
    }

    @DisplayName("должен ожидаемо устанавливать родительский контекст для создаваемого контекста")
    @Test
    fun `should expectedly set parent scope for creating scope`() {
        val scope1 = IoC.resolve<Scope>(Dependency("IoC.Scope.Create"))
        val scope2 = IoC.resolve<Scope>(Dependency("IoC.Scope.Create"), scope1)

        IoC.resolve<Command>(Dependency("IoC.Scope.Current.Set"), scope1).execute();
        IoC.resolve<Command>(Dependency("IoC.Register"), Dependency("someDependency"), { _: Any -> 2 }).execute()
        IoC.resolve<Command>(Dependency("IoC.Scope.Current.Set"), scope2).execute();

        assertThat(IoC.resolve<Int>(Dependency("someDependency"))).isEqualTo(2)
    }
}
