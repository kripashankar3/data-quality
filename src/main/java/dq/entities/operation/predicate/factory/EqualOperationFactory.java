package dq.entities.operation.predicate.factory;

import dq.entities.operation.Operation;
import dq.entities.operation.operand.Operand;
import dq.entities.operation.predicate.EqualOperation;

public class EqualOperationFactory implements PredicateOperationFactory {
    @Override
    public Operation create(Operand left, Operand right) {
        return new EqualOperation(left, right);
    }
}
