package dq.entities.operation.aggregate;

import dq.entities.operation.Operation;
import dq.entities.operation.operand.Operand;
import org.apache.spark.sql.Column;

import static org.apache.spark.sql.functions.count;

public class CountOperation extends BaseAggregateOperation {

    public CountOperation(Operand operand, String alias) {
        super(operand, alias, null);
    }

    public CountOperation(Operand operand, String alias, Operation predicate) {
        super(operand, alias, predicate);
    }

    @Override
    public Column evaluate() {
        return count(toColumn()).alias(alias);
    }

    @Override
    public String expression() {
        return format("COUNT");
    }
}