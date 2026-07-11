package dq.entities.operation.predicate;

import dq.entities.operation.operand.Operand;
import org.apache.spark.sql.Column;

public class LessThanEqualOperation extends PredicateOperation {

    public LessThanEqualOperation(Operand left, Operand right) {
        super(left, right);
    }

    @Override
    public Column evaluate() {
        return left.toColumn().leq(right.toColumn());
    }

    @Override
    public String expression() {
        return String.format("(%s <= %s)",
                left.expression(),
                right.expression());
    }
}
