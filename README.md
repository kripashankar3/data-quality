# Data-quality

Data Quality is a rule evaluation engine based on Apache spark. User can evaluate configured rules against an spark
dataset and generate evaluation result which would contain row wise result of evaluation as success and failure

# Sample Rule DSL

Rule Name = active_employee_salary

```
{
"operands": {
"salary": {
"type": "column",
"name": "salary"
},
"department": {
"type": "column",
"name": "department"
},
"active": {
"type": "column",
"name": "active"
},
"country": {
"type": "column",
"name": "country"
},
"active_literal": {
"type": "literal",
"value": true
},
"india_literal": {
"type": "literal",
"value": "IN"
},
"usa_literal": {
"type": "literal",
"value": "US"
}
},

"groupBy": [
"department"
],

"operation": {

    "sum": {
      "operand": "salary",
      "alias": "total_salary",

      "where": {

        "and": [

          {
            "equal": {
              "left": "active",
              "right": "active_literal"
            }
          },

          {
            "or": [

              {
                "equal": {
                  "left": "country",
                  "right": "india_literal"
                }
              },

              {
                "equal": {
                  "left": "country",
                  "right": "usa_literal"
                }
              }

            ]
          }

        ]
      }
    }

}
}
```

Respective Operation instance

```
AggregationContext aggregation = new AggregationContext(
                new NormalGroupBySpec(List.Of("department"),
                List.of(
                        new SumOperation(
                                new ColumnOperand("salary"),
                                "total_salary",
                                new AndOperation(
                                        new EqualOperation(
                                                new ColumnOperand("active"),
                                                new LiteralOperand(true)
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
```

# Sample test run

Below test shares how we can use Apache Spark API to execute the transformed rule DSL into an operation instance and
assert on it.

```
@Test
    @DisplayName("Should get salary sum by employee's either residing in US or IN and active ones")
    void getSalaryForGenZAndUSEmployee() {
        AggregationContext aggregation = new AggregationContext(
                new NormalGroupBySpec(List.Of("department"),
                List.of(
                        new SumOperation(
                                new ColumnOperand("salary"),
                                "total_salary",
                                new AndOperation(
                                        new EqualOperation(
                                                new ColumnOperand("active"),
                                                new LiteralOperand(true)
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

```