package dq.entities.operation;

import org.apache.spark.sql.Column;

public interface Operation {
    Column evaluate();

    String expression();
}
