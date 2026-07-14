package dq.entities.operation.composite.factory;

import dq.entities.operation.Operation;

public interface CompositeOperationFactory {
    Operation create(Operation... children);
}
