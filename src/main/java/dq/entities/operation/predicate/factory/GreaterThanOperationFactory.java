package dq.entities.operation.predicate.factory;

import dq.entities.operation.Operation;
import dq.entities.operation.operand.Operand;
import dq.entities.operation.predicate.GreaterThanOperation;

public class GreaterThanOperationFactory implements PredicateOperationFactory {
    @Override
    public Operation create(Operand left, Operand right) {
        return new GreaterThanOperation(left, right);
    }
}
