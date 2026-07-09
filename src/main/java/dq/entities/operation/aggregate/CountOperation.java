package dq.entities.operation.aggregate;

import dq.entities.operation.Operand;
import org.apache.spark.sql.Column;

import static org.apache.spark.sql.functions.count;
import static org.apache.spark.sql.functions.min;

public class CountOperation extends BaseAggregateOperation {

    public CountOperation(Operand operand, String alias) {
        super(operand, alias);
    }

    @Override
    public Column evaluate() {
        return count(operand.toColumn()).alias(alias);
    }

    @Override
    public String expression() {
        return format("COUNT");
    }
}