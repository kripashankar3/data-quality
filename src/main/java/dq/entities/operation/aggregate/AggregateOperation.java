package dq.entities.operation.aggregate;

import dq.entities.operation.Operation;

public interface AggregateOperation extends Operation {
    String alias();
}