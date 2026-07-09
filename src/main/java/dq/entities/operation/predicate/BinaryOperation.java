package dq.entities.operation.predicate;

import dq.entities.operation.Operand;
import dq.entities.operation.Operation;

public abstract class BinaryOperation implements Operation {

    protected final Operand left;
    protected final Operand right;

    protected BinaryOperation(Operand left, Operand right) {
        this.left = left;
        this.right = right;
    }
}