package dq.entities.operation.aggregate;

import dq.entities.operation.Operation;
import dq.entities.operation.operand.Operand;
import org.apache.spark.sql.Column;

import static org.apache.spark.sql.functions.max;

public class MaxOperation extends BaseAggregateOperation {

    public MaxOperation(Operand operand, String alias) {
        super(operand, alias, null);
    }

    public MaxOperation(Operand operand, String alias, Operation predicate) {
        super(operand, alias, predicate);
    }

    @Override
    public Column evaluate() {
        return max(toColumn()).alias(alias);
    }

    @Override
    public String expression() {
        return format("MAX");
    }
}