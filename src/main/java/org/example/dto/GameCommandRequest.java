package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameCommandRequest implements Serializable {
    private String gameId;
    private String objectId;
    private String operationId;
    private Map<String, Object> args;
}
