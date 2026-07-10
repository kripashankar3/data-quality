package dq.entities.operation.aggregate;

import dq.entities.operation.Operand;
import org.apache.spark.sql.Column;

import static org.apache.spark.sql.functions.count_distinct;

public class CountDistinctOperation extends BaseAggregateOperation {

    public CountDistinctOperation(Operand operand, String alias) {
        super(operand, alias);
    }

    @Override
    public Column evaluate() {
        return count_distinct(operand.toColumn()).alias(alias);
    }



    @Override
    public String expression() {
        return format("COUNT");
    }
}