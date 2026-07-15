package dq.enums;

import dq.entities.operation.predicate.factory.*;

public enum PredicateType {
    CONTAINS("contains", new ContainsOperationFactory()),
    EQUAL("equal", new EqualOperationFactory()),
    GEQ("geq", new GreaterThanEqualOperationFactory()),
    GT("gt", new GreaterThanOperationFactory()),
    IN("in", new InOperationFactory()),
    NOT_EQUAL("not_equal", new NotEqualOperationFactory()),
    LEQ("leq", new LessThanEqualOperationFactory()),
    STARTS_WITH("starts_with", new StartsWithOperationFactory()),
    LT("lt", new LessThanOperationFactory());

    private final String operation;
    private final PredicateOperationFactory operationFactory;

    PredicateType(String operation, PredicateOperationFactory operationFactory) {
        this.operation = operation;
        this.operationFactory = operationFactory;
    }

    public String operation() {
        return operation;
    }

    public PredicateOperationFactory operationFactory() {
        return operationFactory;
    }
}
