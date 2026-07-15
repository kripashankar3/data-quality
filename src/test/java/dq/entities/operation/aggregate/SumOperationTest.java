package dq.entities.operation.aggregate;

import dq.BaseSetup;
import dq.entities.operation.operand.ColumnOperand;
import dq.entities.operation.operand.LiteralOperand;
import dq.entities.operation.predicate.EqualOperation;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SumOperationTest extends BaseSetup {

    @Test
    @DisplayName("Should get sum of all employee's salary")
    void getTotalSalary() {
        AggregateOperation operation = new SumOperation(
                new ColumnOperand("salary"),
                "total_salary");
        Dataset<Row> result = employeeDataset.
                agg(operation.evaluate());
        assertEquals(1108000.0, result.first().getDouble(0));
        assertEquals(
                "SUM(salary) AS total_salary",
                operation.expression());
    }

    @Test
    @DisplayName("Should get salary sum by department")
    void getSalarySumByDepartment() {
        AggregationContext aggregation = new AggregationContext(
                new NormalGroupBySpec(List.of("department")),
                List.of(
                        new SumOperation(
                                new ColumnOperand("salary"),
                                "total_salary"
                        )
                )
        );

        Dataset<Row> result = aggregation.execute(employeeDataset);
        assertEquals(3, result.count());
        assertEquals(523000.0, result.filter("department=='Engineering'").first().getDouble(1));
        assertEquals(155000.0, result.filter("department=='HR'").first().getDouble(1));
        assertEquals(430000.0, result.filter("department=='Finance'").first().getDouble(1));
    }

    @Test
    @DisplayName("Should get salary sum by department where employee is active")
    void getMinSalaryByActiveEmployee() {
        AggregationContext aggregation = new AggregationContext(
                new NormalGroupBySpec(List.of("department")),
                List.of(
                        new SumOperation(
                                new ColumnOperand("salary"),
                                "total_salary",
                                new EqualOperation(
                                        new ColumnOperand("active"),
                                        new LiteralOperand(true)
                                )
                        )
                )
        );

        Dataset<Row> result = aggregation.execute(employeeDataset);
        assertEquals(3, result.count());
        assertEquals(438000.0, result.filter("department=='Engineering'").first().getDouble(1));
        assertNull(result.filter("department=='HR'").first().get(1));
        assertEquals(430000.0, result.filter("department=='Finance'").first().getDouble(1));
    }
}