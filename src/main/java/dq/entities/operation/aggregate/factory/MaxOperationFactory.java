package dq.entities.operation.aggregate.factory;

import dq.entities.operation.Operation;
import dq.entities.operation.aggregate.AggregateOperation;
import dq.entities.operation.aggregate.MaxOperation;
import dq.entities.operation.operand.Operand;

public class MaxOperationFactory implements AggregateOperationFactory {
    @Override
    public AggregateOperation create(Operand operand, String alias, Operation predicate) {
        return new MaxOperation(operand, alias, predicate);
    }
}
