package dq.entities.rule;

import dq.entities.operation.operand.Operand;

import java.util.Map;
import java.util.Objects;

public record MapOperandResolver(Map<String, Operand> operands) implements OperandResolver {

    public MapOperandResolver(Map<String, Operand> operands) {
        this.operands = Objects.requireNonNull(operands);
    }

    @Override
    public Operand resolve(String name) {

        Operand operand = operands.get(name);

        if (operand == null) {
            throw new IllegalArgumentException(
                    "Unknown operand: " + name
            );
        }

        return operand;
    }
}