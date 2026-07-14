package dq.entities.rule.factory;

import dq.entities.operation.Operation;
import dq.entities.operation.aggregate.AggregateOperation;
import dq.entities.operation.operand.Operand;

public interface AggregateFactory {

    AggregateOperation create(
            Operand operand,
            String alias,
            Operation predicate);

}