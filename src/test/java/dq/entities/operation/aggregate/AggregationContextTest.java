package dq.entities.operation.aggregate;

import dq.entities.BaseSetup;
import dq.entities.operation.operand.ColumnOperand;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AggregationContextTest extends BaseSetup {

    @Test
    @DisplayName("should aggregate over the whole dataset when the group by spec is empty")
    void aggregatesOverWholeDatasetWhenGroupBySpecIsEmpty() {
        AggregationContext context = new AggregationContext(
                new NormalGroupBySpec(List.of()),
                List.of(new CountOperation(new ColumnOperand("name"), "employee_count"))
        );

        Dataset<Row> result = context.execute(employeeDataset);
        assertEquals(1, result.count());
        assertEquals(10, result.first().getLong(0));
    }

    @Test
    @DisplayName("should aggregate per group when the group by spec is not empty")
    void aggregatesPerGroupWhenGroupBySpecIsNotEmpty() {
        AggregationContext context = new AggregationContext(
                new NormalGroupBySpec(List.of("department")),
                List.of(new CountOperation(new ColumnOperand("name"), "employee_count"))
        );

        Dataset<Row> result = context.execute(employeeDataset);
        assertEquals(3, result.count());
    }

    @Test
    @DisplayName("should combine multiple aggregate operations into a single result row")
    void combinesMultipleAggregateOperationsIntoSingleResult() {
        AggregationContext context = new AggregationContext(
                new NormalGroupBySpec(List.of()),
                List.of(
                        new CountOperation(new ColumnOperand("name"), "employee_count"),
                        new SumOperation(new ColumnOperand("salary"), "total_salary")
                )
        );

        Row row = context.execute(employeeDataset).first();
        assertEquals(10, row.getLong(0));
        assertEquals(1108000.0, row.getDouble(1), 0.001);
    }

    @Test
    @DisplayName("should throw when no aggregate operations are provided")
    void throwsWhenNoOperationsProvided() {
        AggregationContext context = new AggregationContext(
                new NormalGroupBySpec(List.of()),
                List.of()
        );

        assertThrows(IllegalArgumentException.class, () -> context.execute(employeeDataset));
    }
}