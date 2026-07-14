package dq.entities.operation.composite.factory;

import dq.entities.operation.Operation;
import dq.entities.operation.composite.OrOperation;

public class OrOperationFactory implements CompositeOperationFactory {
    @Override
    public Operation create(Operation... children) {
        return new OrOperation(children);
    }
}

