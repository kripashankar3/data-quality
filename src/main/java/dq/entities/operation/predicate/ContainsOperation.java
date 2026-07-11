package dq.entities.operation.predicate;

import dq.entities.operation.operand.Operand;
import org.apache.spark.sql.Column;

public class ContainsOperation extends PredicateOperation {

    public ContainsOperation(Operand left, Operand right) {
        super(left, right);
    }

    @Override
    public Column evaluate() {
        return left.toColumn().contains(right.toColumn());
    }

    @Override
    public String expression() {
        return String.format("(%s <> %s)",
                left.expression(),
                right.expression());
    }
}