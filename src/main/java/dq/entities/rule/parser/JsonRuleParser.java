package dq.entities.rule.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dq.entities.operation.Operation;
import dq.entities.rule.OperandResolver;
import dq.entities.rule.OperationRegistry;
import dq.entities.rule.factory.OperationRegistryFactory;

import java.util.Objects;

public class JsonRuleParser implements RuleParser {

    private final ObjectMapper objectMapper;

    public JsonRuleParser(ObjectMapper objectMapper) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }

    @Override
    public Operation parse(String json) throws Exception {
        OperandResolver operandResolver = new OperandParser(objectMapper).parse(json);
        OperationRegistry operationRegistry = OperationRegistryFactory.create();
        OperationParser operationParser = new OperationParser(operationRegistry, operandResolver);

        JsonNode root = objectMapper.readTree(json);
        JsonNode operationNode = root.path("operation");
        Operation operation = operationParser.parse(operationNode);
        return operation;
    }
}
