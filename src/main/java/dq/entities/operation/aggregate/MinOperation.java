package dq.entities.operation.aggregate;

import dq.entities.operation.Operand;
import org.apache.spark.sql.Column;

import static org.apache.spark.sql.functions.min;

public class MinOperation extends BaseAggregateOperation {

    public MinOperation(Operand operand, String alias) {
        super(operand, alias);
    }

    @Override
    public Column evaluate() {
        return min(operand.toColumn()).alias(alias);
    }

    @Override
    public String expression() {
        return format("MIN");
    }
}