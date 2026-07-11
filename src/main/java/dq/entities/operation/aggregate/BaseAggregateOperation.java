package dq.entities.operation.aggregate;

import dq.entities.operation.Operation;
import dq.entities.operation.operand.Operand;
import org.apache.spark.sql.Column;

import static org.apache.spark.sql.functions.lit;
import static org.apache.spark.sql.functions.when;

public abstract class BaseAggregateOperation implements AggregateOperation {

    protected final Operand operand;
    protected final String alias;
    protected final Operation predicate;

    protected BaseAggregateOperation(Operand operand, String alias, Operation predicate) {
        this.operand = operand;
        this.alias = alias;
        this.predicate = predicate;
    }

    protected Column toColumn() {
        Column operandColumn = operand.toColumn();
        return when(
                predicate == null ? lit(true) : predicate.evaluate(),
                operandColumn
        ).otherwise(null);
    }


    @Override
    public String alias() {
        return alias;
    }

    protected String format(String function) {

        return String.format(
                "%s(%s) AS %s",
                function,
                operand.expression(),
                alias
        );
    }
}