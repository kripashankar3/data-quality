package dq.entities.rule;

import dq.entities.operation.aggregate.factory.AggregateOperationFactory;
import dq.entities.operation.composite.factory.CompositeOperationFactory;
import dq.entities.operation.predicate.factory.PredicateOperationFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OperationRegistry {

    private final Map<String, PredicateOperationFactory> predicates;
    private final Map<String, AggregateOperationFactory> aggregates;
    private final Map<String, CompositeOperationFactory> composites;

    private String operation;

    public OperationRegistry() {
        this.predicates = new HashMap<>();
        this.aggregates = new HashMap<>();
        this.composites = new HashMap<>();
    }

    public void registerPredicate(
            String operation,
            PredicateOperationFactory factory) {

        predicates.put(
                Objects.requireNonNull(operation),
                Objects.requireNonNull(factory)
        );
    }

    public void registerAggregate(
            String operation,
            AggregateOperationFactory factory) {

        aggregates.put(
                Objects.requireNonNull(operation),
                Objects.requireNonNull(factory)
        );
    }

    public void registerComposite(
            String operation,
            CompositeOperationFactory factory) {

        composites.put(
                Objects.requireNonNull(operation),
                Objects.requireNonNull(factory)
        );
    }


    public boolean isPredicate(String operation) {
        return predicates.containsKey(operation);
    }

    public boolean isAggregate(String operation) {
        return aggregates.containsKey(operation);
    }

    public boolean isComposite(String operation) {
        return composites.containsKey(operation);
    }

    public PredicateOperationFactory predicate(String operation) {
        PredicateOperationFactory factory = predicates.get(operation);

        if (factory == null) {
            throw new IllegalArgumentException(
                    "Unsupported predicate operation: " + operation
            );
        }

        return factory;
    }


    public AggregateOperationFactory aggregate(String operation) {
        AggregateOperationFactory factory = aggregates.get(operation);

        if (factory == null) {
            throw new IllegalArgumentException(
                    "Unsupported aggregate operation: " + operation
            );
        }

        return factory;
    }


    public CompositeOperationFactory composite(String operation) {
        CompositeOperationFactory factory = composites.get(operation);

        if (factory == null) {
            throw new IllegalArgumentException(
                    "Unsupported composite operation: " + operation
            );
        }

        return factory;
    }
}