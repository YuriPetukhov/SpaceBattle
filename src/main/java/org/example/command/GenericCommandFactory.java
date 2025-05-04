package org.example.command;

import org.example.adapter.IUObject;
import org.example.dto.CommandDTO;
import org.springframework.stereotype.Component;

@Component
public class GenericCommandFactory implements CommandFactory {
    @Override
    public String getActionName() {
        return "*";
    }

    @Override
    public Command create(IUObject target, CommandDTO dto) {
        return () -> target.executeCommand(dto.getAction(), dto.getParams());
    }
}
