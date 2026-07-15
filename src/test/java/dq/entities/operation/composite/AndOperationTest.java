package dq.entities.operation.composite;

import dq.BaseSetup;
import dq.entities.operation.Operation;
import dq.entities.operation.aggregate.AggregationContext;
import dq.entities.operation.aggregate.NormalGroupBySpec;
import dq.entities.operation.aggregate.SumOperation;
import dq.entities.operation.operand.ColumnOperand;
import dq.entities.operation.operand.LiteralOperand;
import dq.entities.operation.predicate.*;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AndOperationTest extends BaseSetup {

    @Test
    @DisplayName("Should get salary sum by employee's residing in US and younger than 30yrs")
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
                                        new EqualOperation(
                                                new ColumnOperand("country"),
                                                new LiteralOperand("US")
                                        )
                                )
                        )
                )
        );

        Dataset<Row> result = aggregation.execute(employeeDataset);
        assertEquals(120000.0, result.first().getDouble(0));
    }

    @Test
    @DisplayName("Employee eligibility test")
    void employee_eligibility_test() {
        Operation employeeEligibilityRule =
                new AndOperation(

                        new GreaterThanOperation(
                                new ColumnOperand("age"),
                                new LiteralOperand(30)
                        ),

                        new InOperation(
                                new ColumnOperand("country"),
                                "US",
                                "UK"
                        ),

                        new OrOperation(

                                new ContainsOperation(
                                        new ColumnOperand("email"),
                                        new LiteralOperand("@gmail.com")
                                ),

                                new EqualOperation(
                                        new ColumnOperand("active"),
                                        new LiteralOperand(false)
                                )
                        )
                );
        System.out.println("Expression: " + employeeEligibilityRule.expression());
        assertEquals(2, countMatches(employeeEligibilityRule));
    }

}