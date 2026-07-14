package dq.entities.rule.handler;

import com.fasterxml.jackson.databind.JsonNode;
import dq.entities.operation.Operation;
import dq.entities.rule.parser.OperationParserContext;

public class CompositeOperationHandler implements OperationHandler {
    @Override
    public Operation parse(String operationName, JsonNode node, OperationParserContext context) {
        return null;
    }
}
