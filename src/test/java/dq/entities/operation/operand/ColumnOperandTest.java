package dq.entities.operation.operand;

import dq.BaseSetup;
import org.apache.spark.sql.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ColumnOperandTest extends BaseSetup {

    @Test
    @DisplayName("expression should return the column name")
    void expressionReturnsColumnName() {
        ColumnOperand operand = new ColumnOperand("department");
        assertEquals("department", operand.expression());
    }

    @Test
    @DisplayName("toColumn should resolve to the underlying dataset column value")
    void toColumnResolvesToUnderlyingColumnValue() {
        ColumnOperand operand = new ColumnOperand("name");
        Row firstRow = employeeDataset.select(operand.toColumn()).first();
        assertEquals("John", firstRow.getString(0));
    }
}