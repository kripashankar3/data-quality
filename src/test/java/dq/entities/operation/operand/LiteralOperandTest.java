package dq.entities.operation.operand;

import dq.entities.operation.OperationTest;
import org.apache.spark.sql.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LiteralOperandTest extends OperationTest {

    @Test
    @DisplayName("expression should quote string literals")
    void expressionQuotesStringLiterals() {
        LiteralOperand operand = new LiteralOperand("Engineering");
        assertEquals("'Engineering'", operand.expression());
    }

    @Test
    @DisplayName("expression should not quote non-string literals")
    void expressionDoesNotQuoteNonStringLiterals() {
        LiteralOperand operand = new LiteralOperand(30);
        assertEquals("30", operand.expression());
    }

    @Test
    @DisplayName("toColumn should resolve to the literal value regardless of dataset row")
    void toColumnResolvesToLiteralValue() {
        LiteralOperand operand = new LiteralOperand(100000.0);
        Row firstRow = employeeDataset.select(operand.toColumn()).first();
        assertEquals(100000.0, firstRow.getDouble(0), 0.001);
    }
}