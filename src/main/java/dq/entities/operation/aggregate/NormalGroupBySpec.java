package dq.entities.operation.aggregate;

import dq.utils.SparkGroupingUtils;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.RelationalGroupedDataset;
import org.apache.spark.sql.Row;

import java.util.List;

public class NormalGroupBySpec implements GroupBySpec {

    private final List<String> columns;

    public NormalGroupBySpec(List<String> columns) {
        this.columns = columns;
    }

    @Override
    public RelationalGroupedDataset apply(Dataset<Row> dataset) {

        Column[] groupColumns = columns.stream()
                .map(org.apache.spark.sql.functions::col)
                .toArray(Column[]::new);
        return SparkGroupingUtils.groupBy(dataset, groupColumns);
    }

    @Override
    public boolean isEmpty() {
        return columns.isEmpty();
    }
}