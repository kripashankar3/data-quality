package dq.entities.operation.aggregate;

import dq.entities.operation.Operand;
import org.apache.spark.sql.Column;

import static org.apache.spark.sql.functions.avg;

public class AvgOperation extends BaseAggregateOperation {

    public AvgOperation(Operand operand, String alias) {
        super(operand, alias);
    }

    @Override
    public Column evaluate() {
        return avg(operand.toColumn()).alias(alias);
    }

    @Override
    public String expression() {
        return format("AVG");
    }
}