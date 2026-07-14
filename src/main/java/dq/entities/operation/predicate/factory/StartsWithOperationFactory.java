package dq.entities.operation.predicate.factory;

import dq.entities.operation.Operation;
import dq.entities.operation.operand.Operand;
import dq.entities.operation.predicate.StartsWithOperation;

public class StartsWithOperationFactory implements PredicateOperationFactory {
    @Override
    public Operation create(Operand left, Operand right) {
        return new StartsWithOperation(left, right);
    }
}
