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

class MaxOperationTest extends BaseSetup {

    @Test
    @DisplayName("Should get max salary out of all employee")
    void getMaxSalary() {
        AggregateOperation operation = new MaxOperation(
                new ColumnOperand("salary"),
                "max_salary");
        Dataset<Row> result = employeeDataset.
                agg(operation.evaluate());
        assertEquals(170000.0, result.first().getDouble(0));
        assertEquals(
                "MAX(salary) AS max_salary",
                operation.expression());
    }

    @Test
    @DisplayName("Should get max salary by department")
    void getMaxSalaryByDepartment() {
        AggregationContext aggregation = new AggregationContext(
                new NormalGroupBySpec(List.of("department")),
                List.of(
                        new MaxOperation(
                                new ColumnOperand("salary"),
                                "max_salary"
                        )
                )
        );

        Dataset<Row> result = aggregation.execute(employeeDataset);
        assertEquals(3, result.count());
        assertEquals(130000.0, result.filter("department=='Engineering'").first().getDouble(1));
        assertEquals(80000.0, result.filter("department=='HR'").first().getDouble(1));
        assertEquals(170000.0, result.filter("department=='Finance'").first().getDouble(1));
    }

    @Test
    @DisplayName("Should get max salary by department where employee is active")
    void getMaxSalaryByActiveEmployee() {
        AggregationContext aggregation = new AggregationContext(
                new NormalGroupBySpec(List.of("department")),
                List.of(
                        new MaxOperation(
                                new ColumnOperand("salary"),
                                "max_salary",
                                new EqualOperation(
                                        new ColumnOperand("active"),
                                        new LiteralOperand(true)
                                )
                        )
                )
        );

        Dataset<Row> result = aggregation.execute(employeeDataset);
        assertEquals(3, result.count());
        assertEquals(130000.0, result.filter("department=='Engineering'").first().getDouble(1));
        assertNull(result.filter("department=='HR'").first().get(1));
        assertEquals(170000.0, result.filter("department=='Finance'").first().getDouble(1));
    }

}