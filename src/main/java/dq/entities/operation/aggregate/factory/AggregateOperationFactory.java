package dq.entities.operation.aggregate.factory;

import dq.entities.operation.Operation;
import dq.entities.operation.aggregate.AggregateOperation;
import dq.entities.operation.operand.Operand;


public interface AggregateOperationFactory {
    AggregateOperation create(Operand operand, String alias, Operation predicate);
}
