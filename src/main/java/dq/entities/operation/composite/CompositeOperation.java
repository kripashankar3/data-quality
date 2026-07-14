package dq.entities.operation.composite;

import dq.entities.operation.Operation;

import java.util.List;

public abstract class CompositeOperation implements Operation {

    protected final List<Operation> operations;

    protected CompositeOperation(Operation... operations) {
        this.operations = List.of(operations);
    }
}