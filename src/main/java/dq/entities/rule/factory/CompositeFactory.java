package dq.entities.rule.factory;

import dq.entities.operation.Operation;
import dq.entities.operation.composite.CompositeOperation;

@FunctionalInterface
public interface CompositeFactory {

    CompositeOperation create(Operation... operations);

}

