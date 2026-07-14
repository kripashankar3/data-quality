package dq.entities.operation.aggregate.factory;

import dq.entities.operation.Operation;
import dq.entities.operation.aggregate.AggregateOperation;
import dq.entities.operation.aggregate.CountDistinctOperation;
import dq.entities.operation.operand.Operand;

public class CountDistinctOperationFactory implements AggregateOperationFactory {
    @Override
    public AggregateOperation create(Operand operand, String alias, Operation predicate) {
        return new CountDistinctOperation(operand, alias, predicate);
    }
}
