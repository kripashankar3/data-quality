package dq.entities.operation.predicate;

import dq.entities.operation.Operand;
import org.apache.spark.sql.Column;

public class GreaterThanEqualOperation extends BinaryOperation {

    public GreaterThanEqualOperation(Operand left, Operand right) {
        super(left, right);
    }

    @Override
    public Column evaluate() {
        return left.toColumn().geq(right.toColumn());
    }

    @Override
    public String expression() {
        return String.format("(%s >= %s)",
                left.expression(),
                right.expression());
    }
}