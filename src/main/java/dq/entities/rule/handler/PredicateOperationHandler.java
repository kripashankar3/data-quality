package dq.entities.rule.handler;

import com.fasterxml.jackson.databind.JsonNode;
import dq.entities.operation.Operation;
import dq.entities.operation.operand.Operand;
import dq.entities.rule.parser.OperationParserContext;

public final class PredicateOperationHandler implements OperationHandler {

    @Override
    public Operation parse(String name, JsonNode node, OperationParserContext context) {
        Operand left = context.operandResolver()
                .resolve(
                        node.get("left").asText()
                );

        Operand right =
                context.operandResolver()
                        .resolve(
                                node.get("right").asText()
                        );


        return context.registry()
                .predicate(name)
                .create(left, right);
    }
}