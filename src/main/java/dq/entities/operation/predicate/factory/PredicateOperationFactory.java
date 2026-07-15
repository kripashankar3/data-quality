package dq.entities.operation.predicate.factory;

import dq.entities.operation.Operation;
import dq.entities.operation.operand.Operand;

public interface PredicateOperationFactory {
    Operation create(Operand left, Operand right);
}
