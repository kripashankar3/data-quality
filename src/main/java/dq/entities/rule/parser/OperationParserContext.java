package dq.entities.rule.parser;

import dq.entities.rule.OperandResolver;
import dq.entities.rule.OperationRegistry;

public record OperationParserContext(OperationRegistry registry, OperandResolver operandResolver) {
}