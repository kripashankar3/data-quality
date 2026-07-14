package dq.entities.rule;

import dq.entities.operation.operand.Operand;

public interface OperandResolver {

    Operand resolve(String name);

}