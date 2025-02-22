package org.example.ioc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class IoCContainerTest {


    @BeforeEach
    void setUp() {
        IoCContainer.Resolve("Scopes.New", "global");
    }

    @Test
    void testScopes() {
        IoCContainer.Resolve("IoC.Register", "global.dependency", (Function<Object[], Object>) (args) -> "Global");

        IoCContainer.Resolve("Scopes.New", "scope1");

        IoCContainer.Resolve("IoC.Register", "scoped.dependency", (Function<Object[], Object>) (args) -> "Scoped", "scope1");

        assertEquals("Global", IoCContainer.Resolve("global.dependency"));

        assertEquals("Scoped", IoCContainer.Resolve("scoped.dependency"));

        IoCContainer.Resolve("Scopes.New", "global");

        assertThrows(RuntimeException.class, () -> IoCContainer.Resolve("scoped.dependency"));
    }

    @Test
    void testInvalidKey() {
        assertThrows(RuntimeException.class, () -> IoCContainer.Resolve("invalid.key"));
    }

    @Test
    void testSingleton() {
        IoCContainer.Resolve("IoC.Register.Singleton", "singleton.dependency", (Function<Object[], Object>) (args) -> new Object());

        Object instance1 = IoCContainer.Resolve("singleton.dependency");
        Object instance2 = IoCContainer.Resolve("singleton.dependency");

        assertSame(instance1, instance2);
    }

    @Test
    void testDependencyWithArguments() {
        IoCContainer.Resolve("IoC.Register", "dependency.with.args", (Function<Object[], Object>) (args) -> "Hello, " + args[0]);

        String result = (String) IoCContainer.Resolve("dependency.with.args", "World");

        assertEquals("Hello, World", result);
    }

    @Test
    void testScopeCleanup() {
        IoCContainer.Resolve("Scopes.New", "scope1");

        IoCContainer.Resolve("IoC.Register", "scoped.dependency", (Function<Object[], Object>) (args) -> "Scoped", "scope1");

        IoCContainer.Resolve("Scopes.New", "global");

        assertThrows(RuntimeException.class, () -> IoCContainer.Resolve("scoped.dependency"));
    }

}