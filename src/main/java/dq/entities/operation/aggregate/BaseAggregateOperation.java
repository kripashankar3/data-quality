package dq.entities.operation.aggregate;

import dq.entities.operation.Operand;

public abstract class BaseAggregateOperation implements AggregateOperation {

    protected final Operand operand;
    protected final String alias;

    protected BaseAggregateOperation(Operand operand, String alias) {
        this.operand = operand;
        this.alias = alias;
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