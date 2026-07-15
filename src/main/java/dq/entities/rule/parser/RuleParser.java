package dq.entities.rule.parser;

import dq.entities.operation.Operation;

public interface RuleParser {
    Operation parse(String json) throws Exception;
}