package dq.entities.operation.predicate;

import dq.entities.operation.Operand;
import org.apache.spark.sql.Column;

public class GreaterThanOperation extends BinaryOperation {

    public GreaterThanOperation(Operand left, Operand right) {
        super(left, right);
    }

    @Override
    public Column evaluate() {
        return left.toColumn().gt(right.toColumn());
    }

    @Override
    public String expression() {
        return String.format("(%s > %s)",
                left.expression(),
                right.expression());
    }
}