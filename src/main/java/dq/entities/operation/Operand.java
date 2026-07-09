package dq.entities.operation;

import org.apache.spark.sql.Column;

public interface Operand {
    Column toColumn();

    String expression();
}