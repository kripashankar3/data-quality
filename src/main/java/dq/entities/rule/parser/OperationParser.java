package dq.entities.rule.parser;

import com.fasterxml.jackson.databind.JsonNode;
import dq.entities.operation.Operation;
import dq.entities.operation.operand.Operand;
import dq.entities.rule.OperandResolver;
import dq.entities.rule.OperationRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class OperationParser {

    private final OperationRegistry registry;
    private final OperandResolver operandResolver;

    public OperationParser(OperationRegistry registry, OperandResolver operandResolver) {
        this.registry = Objects.requireNonNull(registry);
        this.operandResolver = Objects.requireNonNull(operandResolver);
    }


    public Operation parse(JsonNode node) throws IllegalArgumentException {
        if (node == null || node.isNull()) {
            return null;
        }

        String operation = node.fieldNames().next();
        JsonNode body = node.get(operation);

        if (registry.isAggregate(operation)) {
            return parseAggregate(operation, body);
        }

        if (registry.isPredicate(operation)) {
            return parsePredicate(operation, body);
        }

        if (registry.isComposite(operation)) {
            return parseComposite(operation, body);
        }

        throw new IllegalArgumentException("Unsupported operation: " + operation);
    }

    private Operation parsePredicate(String operation, JsonNode node) {

        Operand left = operandResolver.resolve(node.get("left").asText());
        Operand right = operandResolver.resolve(node.get("right").asText());

        return registry
                .predicate(operation)
                .create(left, right);
    }

    private Operation parseAggregate(String operation, JsonNode node) {

        Operand operand = operandResolver.resolve(node.get("operand").asText());
        String alias = node.get("alias").asText();
        Operation where = null;

        if (node.has("where")) {
            where = parse(node.get("where"));
        }

        return registry
                .aggregate(operation)
                .create(
                        operand,
                        alias,
                        where
                );
    }

    private Operation parseComposite(
            String operation,
            JsonNode node) {

        List<Operation> children = new ArrayList<>();

        for (JsonNode child : node) {
            children.add(parse(child));
        }

        return registry
                .composite(operation)
                .create(
                        children.toArray(Operation[]::new)
                );
    }
}