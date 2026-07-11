package dq.entities.operation.aggregate;

import org.apache.spark.sql.Column;

public interface AggregateOperation {

    Column evaluate();

    String alias();

    String expression();
}