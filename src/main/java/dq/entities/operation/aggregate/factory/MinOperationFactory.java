package dq.entities.operation.aggregate.factory;

import dq.entities.operation.Operation;
import dq.entities.operation.aggregate.AggregateOperation;
import dq.entities.operation.aggregate.MinOperation;
import dq.entities.operation.operand.Operand;

public class MinOperationFactory implements AggregateOperationFactory {
    @Override
    public AggregateOperation create(Operand operand, String alias, Operation predicate) {
        return new MinOperation(operand, alias, predicate);
    }
}
