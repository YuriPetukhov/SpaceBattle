package org.example.ioc;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class IoCContainerConcurrencyTest {

    @Test
    void testConcurrentScopes() throws InterruptedException {
        int threadCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        AtomicInteger successCount = new AtomicInteger(0);

        for (int i = 0; i < threadCount; i++) {
            int threadId = i;
            executor.submit(() -> {
                try {
                    IoCContainer.Resolve("Scopes.New", "scope" + threadId);

                    IoCContainer.Resolve("IoC.Register", "thread.dependency", (Function<Object[], Object>) (args) -> "Thread " + threadId, "scope" + threadId);

                    String result = (String) IoCContainer.Resolve("thread.dependency");

                    assertEquals("Thread " + threadId, result);
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    fail("Thread " + threadId + " failed: " + e.getMessage());
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        assertEquals(threadCount, successCount.get());
    }

    @Test
    void testConcurrentSingletons() throws InterruptedException {
        int threadCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        AtomicInteger successCount = new AtomicInteger(0);

        IoCContainer.Resolve("IoC.Register.Singleton", "concurrent.singleton", (Function<Object[], Object>) (args) -> new Object());

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    Object instance1 = IoCContainer.Resolve("concurrent.singleton");
                    Object instance2 = IoCContainer.Resolve("concurrent.singleton");

                    assertSame(instance1, instance2);
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    fail("Thread failed: " + e.getMessage());
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        assertEquals(threadCount, successCount.get());
    }

    @Test
    void testConcurrentScopeIsolation() throws InterruptedException {
        int threadCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        AtomicInteger successCount = new AtomicInteger(0);

        for (int i = 0; i < threadCount; i++) {
            int threadId = i;
            executor.submit(() -> {
                try {
                    IoCContainer.Resolve("Scopes.New", "scope" + threadId);

                    IoCContainer.Resolve("IoC.Register", "thread.dependency", (Function<Object[], Object>) (args) -> "Thread " + threadId, "scope" + threadId);

                    String result = (String) IoCContainer.Resolve("thread.dependency");

                    assertEquals("Thread " + threadId, result);
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    fail("Thread " + threadId + " failed: " + e.getMessage());
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        assertEquals(threadCount, successCount.get());
    }
}
