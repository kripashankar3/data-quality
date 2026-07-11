package dq.entities.operation.operand;

import org.apache.spark.sql.Column;

import static org.apache.spark.sql.functions.col;

public class ColumnOperand implements Operand {

    private final String columnName;

    public ColumnOperand(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public Column toColumn() {
        return col(columnName);
    }

    @Override
    public String expression() {
        return columnName;
    }
}
