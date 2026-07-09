package dq.entities.operation.aggregate;

import dq.entities.operation.Operand;
import org.apache.spark.sql.Column;

import static org.apache.spark.sql.functions.max;

public class MaxOperation extends BaseAggregateOperation {

    public MaxOperation(Operand operand, String alias) {
        super(operand, alias);
    }

    @Override
    public Column evaluate() {
        return max(operand.toColumn()).alias(alias);
    }

    @Override
    public String expression() {
        return format("MAX");
    }
}