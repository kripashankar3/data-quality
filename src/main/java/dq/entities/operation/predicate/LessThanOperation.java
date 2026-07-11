package dq.entities.operation.predicate;

import dq.entities.operation.operand.Operand;
import org.apache.spark.sql.Column;

public class LessThanOperation extends PredicateOperation {

    public LessThanOperation(Operand left, Operand right) {
        super(left, right);
    }

    @Override
    public Column evaluate() {
        return left.toColumn().lt(right.toColumn());
    }

    @Override
    public String expression() {
        return String.format("(%s < %s)",
                left.expression(),
                right.expression());
    }
}
