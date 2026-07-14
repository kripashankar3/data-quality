package dq.entities.operation.composite.factory;

import dq.entities.operation.Operation;
import dq.entities.operation.composite.AndOperation;

public class AndOperationFactory implements CompositeOperationFactory {
    @Override
    public Operation create(Operation... children) {
        return new AndOperation(children);
    }
}
