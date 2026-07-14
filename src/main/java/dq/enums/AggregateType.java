package dq.enums;

import dq.entities.operation.aggregate.factory.*;

public enum AggregateType {
    AVG("avg", new AvgOperationFactory()),
    SUM("sum", new SumOperationFactory()),
    COUNT_DISTINCT("count_distinct", new CountDistinctOperationFactory()),
    COUNT("count", new CountOperationFactory()),
    MAX("max", new MaxOperationFactory()),
    MIN("min", new MinOperationFactory()),
    ;

    private final String operation;
    private final AggregateOperationFactory operationFactory;

    AggregateType(String operation, AggregateOperationFactory operationFactory) {
        this.operation = operation;
        this.operationFactory = operationFactory;
    }

    public String operation() {
        return operation;
    }

    public AggregateOperationFactory operationFactory() {
        return operationFactory;
    }
}

