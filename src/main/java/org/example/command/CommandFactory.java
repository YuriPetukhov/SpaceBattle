package org.example.command;

import org.example.adapter.IUObject;
import org.example.dto.CommandDTO;

public interface CommandFactory {
    String getActionName();
    Command create(IUObject target, CommandDTO dto);
}
