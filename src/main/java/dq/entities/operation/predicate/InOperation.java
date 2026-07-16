package dq.entities.operation.predicate;

import dq.entities.operation.operand.LiteralOperand;
import dq.entities.operation.operand.Operand;
import org.apache.spark.sql.Column;

import java.util.List;

public class InOperation extends PredicateOperation {

    public InOperation(Operand left, Operand right) {
        super(left, right);
    }

    @Override
    public Column evaluate() {
        if (right instanceof LiteralOperand literal) {
            Object value = literal.value();

            if (value instanceof List<?> list) {
                return left.toColumn().isin(list.toArray());
            }
        }

        throw new IllegalArgumentException("In operation requires a list literal");
    }

    @Override
    public String expression() {
        return String.format("(%s IN (%s))",
                left.expression(),
                right.expression());
    }
}