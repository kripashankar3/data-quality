package dq.entities.operation.predicate;

import dq.entities.operation.Operand;
import org.apache.spark.sql.Column;

public class EqualOperation extends BinaryOperation {

    public EqualOperation(Operand left, Operand right) {
        super(left, right);
    }


    @Override
    public Column evaluate() {
        return left.toColumn().equalTo(right.toColumn());
    }

    @Override
    public String expression() {
        return String.format("(%s == %s)",
                left.expression(),
                right.expression());
    }
}
