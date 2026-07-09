package dq.entities.operation.predicate;

import dq.entities.operation.Operand;
import dq.entities.operation.Operation;
import org.apache.spark.sql.Column;

import java.util.Arrays;

public class InOperation implements Operation {

    private final Operand left;
    private final Object[] values;

    public InOperation(Operand left, Object... values) {
        this.left = left;
        this.values = values;
    }

    @Override
    public Column evaluate() {
        return left.toColumn().isin(values);
    }

    @Override
    public String expression() {
        return String.format("(%s IN '(%s)')",
                left.expression(),
                Arrays.toString(values));
    }
}