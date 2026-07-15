package dq.entities.rule.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dq.entities.operation.Operation;
import dq.entities.operation.aggregate.GroupBySpec;
import dq.entities.operation.aggregate.NormalGroupBySpec;
import dq.entities.operation.aggregate.RollUpGroupBySpec;
import dq.entities.rule.OperandResolver;
import dq.entities.rule.OperationRegistry;
import dq.entities.rule.OperationRegistryFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

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
        return operationParser.parse(operationNode);
    }

    public GroupBySpec getGroupBySpec(String json) throws JsonProcessingException {
        JsonNode root = objectMapper.readTree(json);
        JsonNode groupBySpecNode;
        if (root.has("groupBy")) {
            groupBySpecNode = root.get("groupBy");
            ArrayList<String> cols = parseGroupBySpecCols(groupBySpecNode);
            return new NormalGroupBySpec(cols);
        } else if (root.has("rollUp")) {
            groupBySpecNode = root.get("rollUp");
            ArrayList<String> cols = parseGroupBySpecCols(groupBySpecNode);
            return new RollUpGroupBySpec(cols);
        }
        return null;
    }

    private ArrayList<String> parseGroupBySpecCols(JsonNode groupBySpecNode) {
        ArrayList<String> cols;
        if (groupBySpecNode.isArray()) {
            cols = objectMapper.convertValue(
                    groupBySpecNode,
                    new TypeReference<>() {
                    }
            );
        } else if (groupBySpecNode.isTextual()) {
            cols = Arrays.stream(groupBySpecNode.asText().split(","))
                    .map(String::trim)
                    .collect(Collectors.toCollection(ArrayList::new));
        } else {
            throw new IllegalArgumentException("Invalid groupBy specification, only string or list values are acceptable");
        }
        return cols;
    }
}
