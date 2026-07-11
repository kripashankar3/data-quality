package dq.entities.operation.aggregate;

import dq.entities.operation.OperationTest;
import dq.entities.operation.operand.ColumnOperand;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AvgOperationTest extends OperationTest {

    @Test
    @DisplayName("AvgOperation generates correct Spark SQL expression")
    void getCorrectAvgOperationExpression() {

        AggregateOperation operation = new AvgOperation(
                new ColumnOperand("age"),
                "avg_employee_age");
        Dataset<Row> result = employeeDataset.
                agg(operation.evaluate());
        assertEquals(29.0, result.first().get(0));
        assertEquals(
                "AVG(age) AS avg_employee_age",
                operation.expression());
    }

    @Test
    @DisplayName("Get average employee age by department using group by")
    void getAvgAgeByDepartment() {
        AggregationContext aggregation = new AggregationContext(
                new NormalGroupBySpec(List.of("department")),
                List.of(
                        new AvgOperation(
                                new ColumnOperand("age"),
                                "avg_age"
                        )
                )
        );

        Dataset<Row> result = aggregation.execute(employeeDataset);
        assertEquals(3, result.count());
        assertEquals(24.6, result.filter("department=='Engineering'").first().getDouble(1));
        assertEquals(32.5, result.filter("department=='HR'").first().getDouble(1));
        assertEquals(34.0, result.filter("department=='Finance'").first().getDouble(1));
    }

}