package dq.entities.operation.aggregate;

import dq.entities.BaseSetup;
import dq.entities.operation.operand.ColumnOperand;
import dq.entities.operation.operand.LiteralOperand;
import dq.entities.operation.predicate.GreaterThanOperation;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CountOperationTest extends BaseSetup {

    @Test
    @DisplayName("should get employee count in employee dataset")
    void getEmployeeCountInEmployeeDataset() {
        AggregateOperation operation = new CountOperation(
                new ColumnOperand("department"),
                "dept_count");
        Dataset<Row> result = employeeDataset.
                agg(operation.evaluate());
        assertEquals(10, result.first().getLong(0));
        assertEquals(
                "COUNT(department) AS dept_count",
                operation.expression());
    }

    @Test
    @DisplayName("Get employee count by department using group by")
    void getEmployeeCountByDepartmentUsingGroupBy() {
        AggregationContext aggregation = new AggregationContext(
                new NormalGroupBySpec(List.of("department")),
                List.of(
                        new CountOperation(
                                new ColumnOperand("name"),
                                "employee_count"
                        )
                )
        );

        Dataset<Row> result = aggregation.execute(employeeDataset);
        assertEquals(3, result.count());
        assertEquals(5, result.filter("department=='Engineering'").first().getLong(1));
        assertEquals(2, result.filter("department=='HR'").first().getLong(1));
        assertEquals(3, result.filter("department=='Finance'").first().getLong(1));
    }

    @Test
    @DisplayName("Get employee count having salary more than 100000.0 in each department")
    void getEmployeeCountHavingSalaryMoreThan100000InEachDepartment() {
        AggregationContext aggregation = new AggregationContext(
                new NormalGroupBySpec(List.of("department")),
                List.of(
                        new CountOperation(
                                new ColumnOperand("name"),
                                "employee_count",
                                new GreaterThanOperation(
                                        new ColumnOperand("salary"),
                                        new LiteralOperand(100000.0)
                                )
                        )
                )
        );

        Dataset<Row> result = aggregation.execute(employeeDataset);
        assertEquals(3, result.count());
        assertEquals(2, result.filter("department=='Engineering'").first().getLong(1));
        assertEquals(0, result.filter("department=='HR'").first().getLong(1));
        assertEquals(3, result.filter("department=='Finance'").first().getLong(1));
    }

}