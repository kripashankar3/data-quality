package dq.entities.operation.aggregate;

import dq.entities.operation.Operation;
import dq.entities.operation.operand.Operand;
import org.apache.spark.sql.Column;

import static org.apache.spark.sql.functions.min;

public class MinOperation extends BaseAggregateOperation {

    public MinOperation(Operand operand, String alias) {
        super(operand, alias, null);
    }

    public MinOperation(Operand operand, String alias, Operation predicate) {
        super(operand, alias, predicate);
    }

    @Override
    public Column evaluate() {
        return min(toColumn()).alias(alias);
    }

    @Override
    public String expression() {
        return format("MIN");
    }
}