package dq.entities.operation.predicate;

import dq.entities.operation.Operation;
import dq.entities.operation.operand.Operand;

public abstract class PredicateOperation implements Operation {

    protected final Operand left;
    protected final Operand right;

    protected PredicateOperation(Operand left, Operand right) {
        this.left = left;
        this.right = right;
    }
}