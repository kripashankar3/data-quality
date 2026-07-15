package dq.entities.operation.composite;

import dq.BaseSetup;
import dq.entities.operation.aggregate.AggregationContext;
import dq.entities.operation.aggregate.NormalGroupBySpec;
import dq.entities.operation.aggregate.SumOperation;
import dq.entities.operation.operand.ColumnOperand;
import dq.entities.operation.operand.LiteralOperand;
import dq.entities.operation.predicate.EqualOperation;
import dq.entities.operation.predicate.LessThanOperation;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrOperationTest extends BaseSetup {
    @Test
    @DisplayName("Should get salary sum by employee's either residing in US or IN and younger than 30yrs")
    void getSalaryForGenZAndUSEmployee() {
        AggregationContext aggregation = new AggregationContext(
                new NormalGroupBySpec(Collections.emptyList()),
                List.of(
                        new SumOperation(
                                new ColumnOperand("salary"),
                                "total_salary_genz",
                                new AndOperation(
                                        new LessThanOperation(
                                                new ColumnOperand("age"),
                                                new LiteralOperand(30)
                                        ),
                                        new OrOperation(
                                                new EqualOperation(
                                                        new ColumnOperand("country"),
                                                        new LiteralOperand("US")
                                                ),
                                                new EqualOperation(
                                                        new ColumnOperand("country"),
                                                        new LiteralOperand("IN")
                                                )
                                        )
                                )
                        )
                )
        );

        Dataset<Row> result = aggregation.execute(employeeDataset);
        assertEquals(285000.0, result.first().getDouble(0));
    }
}