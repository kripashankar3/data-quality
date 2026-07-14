package dq.entities.operation.aggregate;

import dq.entities.BaseSetup;
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

class MinOperationTest extends BaseSetup {

    @Test
    @DisplayName("Should get min salary out of all employee")
    void getMinSalary() {
        AggregateOperation operation = new MinOperation(
                new ColumnOperand("salary"),
                "min_salary");
        Dataset<Row> result = employeeDataset.
                agg(operation.evaluate());
        assertEquals(75000.0, result.first().getDouble(0));
        assertEquals(
                "MIN(salary) AS min_salary",
                operation.expression());
    }

    @Test
    @DisplayName("Should get min salary by department")
    void getMinSalaryByDepartment() {
        AggregationContext aggregation = new AggregationContext(
                new NormalGroupBySpec(List.of("department")),
                List.of(
                        new MinOperation(
                                new ColumnOperand("salary"),
                                "min_salary"
                        )
                )
        );

        Dataset<Row> result = aggregation.execute(employeeDataset);
        assertEquals(3, result.count());
        assertEquals(85000.0, result.filter("department=='Engineering'").first().getDouble(1));
        assertEquals(75000.0, result.filter("department=='HR'").first().getDouble(1));
        assertEquals(110000.0, result.filter("department=='Finance'").first().getDouble(1));
    }

    @Test
    @DisplayName("Should get min salary by department where employee is active")
    void getMinSalaryByActiveEmployee() {
        AggregationContext aggregation = new AggregationContext(
                new NormalGroupBySpec(List.of("department")),
                List.of(
                        new MinOperation(
                                new ColumnOperand("salary"),
                                "min_salary",
                                new EqualOperation(
                                        new ColumnOperand("active"),
                                        new LiteralOperand(true)
                                )
                        )
                )
        );

        Dataset<Row> result = aggregation.execute(employeeDataset);
        assertEquals(3, result.count());
        assertEquals(90000.0, result.filter("department=='Engineering'").first().getDouble(1));
        assertNull(result.filter("department=='HR'").first().get(1));
        assertEquals(110000.0, result.filter("department=='Finance'").first().getDouble(1));
    }

}