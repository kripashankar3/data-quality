package dq.entities.operation.aggregate.factory;

import dq.entities.operation.Operation;
import dq.entities.operation.aggregate.AggregateOperation;
import dq.entities.operation.aggregate.CountOperation;
import dq.entities.operation.operand.Operand;

public class CountOperationFactory implements AggregateOperationFactory {
    @Override
    public AggregateOperation create(Operand operand, String alias, Operation predicate) {
        return new CountOperation(operand, alias, predicate);
    }
}
