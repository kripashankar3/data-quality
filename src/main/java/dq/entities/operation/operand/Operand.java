package dq.entities.operation.operand;

import org.apache.spark.sql.Column;

public interface Operand {
    Column toColumn();

    String expression();
}