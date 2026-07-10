package dq.entities.operation.aggregate;

import dq.entities.operation.Operand;
import dq.entities.operation.predicate.PredicateOperation;
import org.apache.spark.sql.Column;

import static org.apache.spark.sql.functions.avg;

public class AvgOperation extends BaseAggregateOperation {

    public AvgOperation(Operand operand, String alias) {
        super(operand, alias, null);
    }

    public AvgOperation(Operand operand, String alias, PredicateOperation predicate) {
        super(operand, alias, predicate);
    }

    @Override
    public Column evaluate() {
        return avg(column()).alias(alias);
    }

    @Override
    public String expression() {
        return format("AVG");
    }
}