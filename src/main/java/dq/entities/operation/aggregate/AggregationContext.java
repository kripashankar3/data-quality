package dq.entities.operation.aggregate;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.Arrays;
import java.util.List;

public class AggregationContext {

    private final GroupBySpec groupBySpec;
    private final List<AggregateOperation> operations;


    public AggregationContext(
            GroupBySpec groupBySpec,
            List<AggregateOperation> operations) {

        this.groupBySpec = groupBySpec;
        this.operations = operations;
    }


    public Dataset<Row> execute(Dataset<Row> dataset) {
        Column[] aggregates = aggregateColumns();

        if (groupBySpec.isEmpty()){
            return dataset.agg(first(aggregates), remaining(aggregates));
        }

        return groupBySpec
                .apply(dataset)
                .agg(first(aggregates), remaining(aggregates));
    }


    private Column[] aggregateColumns() {

        return operations.stream()
                .map(AggregateOperation::evaluate)
                .toArray(Column[]::new);
    }


    private Column first(Column[] columns) {

        if (columns.length == 0) {
            throw new IllegalArgumentException(
                    "At least one aggregate operation is required");
        }

        return columns[0];
    }


    private Column[] remaining(Column[] columns) {

        return Arrays.copyOfRange(
                columns,
                1,
                columns.length
        );
    }
}