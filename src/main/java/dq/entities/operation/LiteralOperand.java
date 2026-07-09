package dq.entities.operation;

import org.apache.spark.sql.Column;

import static org.apache.spark.sql.functions.lit;

public class LiteralOperand implements Operand {

    private final Object value;

    public LiteralOperand(Object value) {
        this.value = value;
    }

    @Override
    public Column toColumn() {
        return lit(value);
    }

    @Override
    public String expression() {
        if (value instanceof String) {
            return "'" + value + "'";
        }
        return String.valueOf(value);
    }
}
