package dq.entities.operation.aggregate;

import dq.entities.operation.Operation;
import dq.entities.operation.operand.Operand;
import org.apache.spark.sql.Column;

import static org.apache.spark.sql.functions.count_distinct;

public class CountDistinctOperation extends BaseAggregateOperation {

    public CountDistinctOperation(Operand operand, String alias) {
        super(operand, alias, null);
    }

    public CountDistinctOperation(Operand operand, String alias, Operation predicate) {
        super(operand, alias, predicate);
    }

    @Override
    public Column evaluate() {
        return count_distinct(toColumn()).alias(alias);
    }

    @Override
    public String expression() {
        return format("COUNT");
    }
}