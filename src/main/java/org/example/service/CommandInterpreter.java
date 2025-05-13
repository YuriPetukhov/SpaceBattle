package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.adapter.IUObject;
import org.example.command.*;
import org.example.dto.CommandDTO;
import org.example.exceptions.type.CommandException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandInterpreter {
    private final Map<String, CommandFactory> factories;
    private final GameRegistry registry;
    private final PlayerScope scope;
    private final GenericCommandFactory genericFactory;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public CommandInterpreter(List<CommandFactory> factoryList,
                              GenericCommandFactory genericFactory,
                              GameRegistry registry,
                              PlayerScope scope) {
        this.factories = factoryList.stream()
                .collect(Collectors.toMap(CommandFactory::getActionName, f -> f));
        this.genericFactory = genericFactory;
        this.registry = registry;
        this.scope = scope;
    }

    public void interpret(String jsonString) {
        try {
            CommandDTO dto = objectMapper.readValue(jsonString, CommandDTO.class);
            IUObject target = registry.get(dto.getId())
                    .orElseThrow(() -> new CommandException("Unknown target: " + dto.getId()));

            String ownerId = target.<String>getProperty("ownerId")
                    .orElseThrow(() -> new CommandException("No ownerId for target: " + dto.getId()));

            if (!scope.isOwnedByCurrentPlayer(ownerId)) {
                throw new CommandException("Unauthorized command on object " + dto.getId());
            }

            CommandFactory factory = factories.getOrDefault(dto.getAction(), genericFactory);
            Command cmd = factory.create(target, dto);
            cmd.execute();
        } catch (Exception e) {
            throw new CommandException(e.getMessage());
        }
    }
}
