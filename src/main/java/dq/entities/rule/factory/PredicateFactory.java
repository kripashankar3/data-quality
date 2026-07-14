package dq.entities.rule.factory;

import dq.entities.operation.operand.Operand;
import dq.entities.operation.predicate.PredicateOperation;

public interface PredicateFactory {

    PredicateOperation create(Operand left, Operand right);

}