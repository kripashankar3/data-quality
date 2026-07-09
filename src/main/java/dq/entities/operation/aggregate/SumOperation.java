package dq.entities.operation.aggregate;

import dq.entities.operation.Operand;
import org.apache.spark.sql.Column;

import static org.apache.spark.sql.functions.sum;

public class SumOperation extends BaseAggregateOperation {

    public SumOperation(Operand operand, String alias) {
        super(operand, alias);
    }

    @Override
    public Column evaluate() {
        return sum(operand.toColumn()).alias(alias);
    }

    @Override
    public String expression() {
        return format("SUM");
    }
}