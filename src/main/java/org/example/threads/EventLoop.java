package org.example.threads;

import lombok.Getter;
import org.example.command.Command;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Getter
@Component
public class EventLoop {
    private final BlockingQueue<Command> queue = new LinkedBlockingQueue<>();
    private final ExecutorService workers;

    private Runnable behaviour; // Для хранения поведения по умолчанию

    // Конструктор для инициализации с количеством потоков
    public EventLoop(int numThreads) {
        this.workers = Executors.newFixedThreadPool(numThreads);
        this.behaviour = this::defaultBehaviour;
    }

    // Поведение по умолчанию, если очередь пуста
    void defaultBehaviour() {
        System.out.println("Очередь пуста. Выполняется стандартное поведение.");
    }

    // Установить новое поведение
    public void setBehaviour(Runnable newBehaviour) {
        this.behaviour = newBehaviour;
    }

    // Запуск потоков для обработки команд
    public void start() {
        for (int i = 0; i < ((ThreadPoolExecutor) workers).getCorePoolSize(); i++) {
            workers.submit(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        Command cmd = queue.take(); // Ждем команду
                        cmd.execute(); // Выполняем команду
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // Восстановление флага прерывания
                    } catch (Exception e) {
                        System.err.println("Ошибка в команде: " + e.getMessage());
                    }
                    // Если очередь пуста, выполняем поведение по умолчанию
                    if (queue.isEmpty()) {
                        behaviour.run(); // Выполнение нового или стандартного поведения
                    }
                }
            });
        }
    }

    // Отправка команды в очередь
    public void submit(Command command) {
        queue.offer(command);
    }

    // Жесткая остановка всех потоков
    public void stop() {
        workers.shutdownNow();
    }

    // Мягкая остановка: ждем завершения всех задач
    public void softStop() throws InterruptedException {
        workers.shutdown();
        workers.awaitTermination(10, TimeUnit.SECONDS);
    }
}

