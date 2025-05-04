package org.example.service;

import org.example.adapter.IUObject;
import org.example.adapter.UObject;
import org.example.command.GameRegistry;
import org.example.command.GenericCommandFactory;
import org.example.command.PlayerScope;
import org.example.exceptions.type.CommandException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class CommandInterpreterTest {
    private CommandInterpreter interpreter;
    private GameRegistry registry;
    private PlayerScope scope;
    private UObject testObj;

    @BeforeEach
    void setUp() {
        registry = new GameRegistry();
        scope = new PlayerScope();

        interpreter = new CommandInterpreter(
                List.of(),
                new GenericCommandFactory(),
                registry,
                scope
        );

        testObj = new UObject();
        testObj.setProperty("ownerId", "player1");
        testObj.setProperty("cmd:Foo", (Consumer<Map<String, Object>>) params ->
                params.put("handled", true)
        );

        registry.register("test", testObj);

        scope.setCurrentPlayer("player1");
    }

    @Test
    void testCustomCommandHandling() {
        Optional<IUObject> objFromRegistry = registry.get("test");
        assertTrue(objFromRegistry.isPresent(), "Test object should be in registry");

        Consumer<Map<String, Object>> mockHandler = Mockito.mock(Consumer.class);
        testObj.setProperty("cmd:Foo", mockHandler);

        String json = "{\"id\":\"test\",\"action\":\"Foo\",\"params\":{}}";
        interpreter.interpret(json);

        Mockito.verify(mockHandler).accept(Mockito.anyMap());
    }

    @Test
    void testUnknownCommand() {
        String json = "{\"id\":\"test\",\"action\":\"UnknownCommand\",\"params\":{}}";
        assertThrows(CommandException.class, () -> interpreter.interpret(json));
    }

    @Test
    void testUnauthorizedCommand() {
        scope.setCurrentPlayer("player2"); // Чужой игрок
        String json = "{\"id\":\"test\",\"action\":\"Foo\",\"params\":{}}";
        assertThrows(CommandException.class, () -> interpreter.interpret(json));
    }
}