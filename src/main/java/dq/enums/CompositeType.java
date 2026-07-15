package dq.enums;

import dq.entities.operation.composite.factory.AndOperationFactory;
import dq.entities.operation.composite.factory.CompositeOperationFactory;
import dq.entities.operation.composite.factory.OrOperationFactory;

public enum CompositeType {
    AND("and", new AndOperationFactory()),
    OR("or", new OrOperationFactory());

    private final String operation;
    private final CompositeOperationFactory operationFactory;

    CompositeType(String operation, CompositeOperationFactory operationFactory) {
        this.operation = operation;
        this.operationFactory = operationFactory;
    }

    public String operation() {
        return operation;
    }

    public CompositeOperationFactory operationFactory() {
        return operationFactory;
    }
}
