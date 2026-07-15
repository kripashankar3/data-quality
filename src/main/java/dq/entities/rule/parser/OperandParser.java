package dq.entities.rule.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dq.entities.operation.operand.ColumnOperand;
import dq.entities.operation.operand.LiteralOperand;
import dq.entities.operation.operand.Operand;
import dq.entities.rule.MapOperandResolver;
import dq.entities.rule.OperandResolver;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public final class OperandParser {

    private final ObjectMapper MAPPER;

    public OperandParser(ObjectMapper objectMapper) {
        MAPPER = Objects.requireNonNull(objectMapper);
    }


    public OperandResolver parse(String json) throws IOException {
        JsonNode root = MAPPER.readTree(json);
        JsonNode operandsNode = root.path("operands");

        if (!operandsNode.isObject()) {
            throw new IllegalArgumentException("Missing or invalid 'operands' section.");
        }

        Map<String, Operand> operands = new LinkedHashMap<>();

        for (Map.Entry<String, JsonNode> entry : operandsNode.properties()) {
            operands.put(entry.getKey(), parseOperand(entry.getValue()));
        }

        return new MapOperandResolver(operands);
    }

    private Operand parseOperand(JsonNode node) {
        String type = node.path("type").asText();

        return switch (type) {
            case "column" -> new ColumnOperand(
                    required(node, "name").asText());
            case "literal" -> new LiteralOperand(
                    readLiteral(required(node, "value")));
            default -> throw new IllegalArgumentException(
                    "Unsupported operand type: " + type);
        };
    }

    private JsonNode required(JsonNode node, String field) {
        JsonNode value = node.get(field);
        if (value == null || value.isMissingNode()) {
            throw new IllegalArgumentException("Missing required field '" + field + "'");
        }
        return value;
    }

    private Object readLiteral(JsonNode valueNode) {
        return valueNode.isNull() ? null : MAPPER.convertValue(valueNode, Object.class);
    }
}