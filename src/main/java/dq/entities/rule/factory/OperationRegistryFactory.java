package dq.entities.rule.factory;

import dq.entities.rule.OperationRegistry;
import dq.enums.AggregateType;
import dq.enums.CompositeType;
import dq.enums.PredicateType;

import java.util.Arrays;

public final class OperationRegistryFactory {

    private OperationRegistryFactory() {
    }

    public static OperationRegistry create() {
        OperationRegistry registry = new OperationRegistry();
        Arrays.stream(AggregateType.values())
                .forEach(aggregateType -> registry.registerAggregate(
                        aggregateType.operation(), aggregateType.operationFactory())
                );
        Arrays.stream(CompositeType.values())
                .forEach(compositeType -> registry.registerComposite(
                        compositeType.operation(), compositeType.operationFactory())
                );
        Arrays.stream(PredicateType.values())
                .forEach(predicateType -> registry.registerPredicate(
                        predicateType.operation(), predicateType.operationFactory())
                );
        return registry;
    }
}