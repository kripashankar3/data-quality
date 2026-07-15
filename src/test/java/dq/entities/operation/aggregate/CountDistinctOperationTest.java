package dq.entities.operation.aggregate;

import dq.BaseSetup;
import dq.entities.operation.operand.ColumnOperand;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CountDistinctOperationTest extends BaseSetup {

    @Test
    @DisplayName("should get distinct department count in employee dataset")
    void getDistinctDepartmentsInEmployeeDataset() {
        AggregateOperation operation = new CountDistinctOperation(
                new ColumnOperand("department"),
                "distinct_dept_count");
        Dataset<Row> result = employeeDataset.
                agg(operation.evaluate());
        assertEquals(3, result.first().getLong(0));
        assertEquals(
                "COUNT(department) AS distinct_dept_count",
                operation.expression());
    }

    @Test
    @DisplayName("Get country count by department using group by")
    void getEmployeeCountByDepartmentUsingGroupBy() {
        AggregationContext aggregation = new AggregationContext(
                new NormalGroupBySpec(List.of("department")),
                List.of(
                        new CountDistinctOperation(
                                new ColumnOperand("country"),
                                "distinct_geo_count"
                        )
                )
        );

        Dataset<Row> result = aggregation.execute(employeeDataset);
        assertEquals(3, result.count());
        assertEquals(4, result.filter("department=='Engineering'").first().getLong(1));
        assertEquals(2, result.filter("department=='HR'").first().getLong(1));
        assertEquals(3, result.filter("department=='Finance'").first().getLong(1));
    }

}