package dq.utils;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.RelationalGroupedDataset;
import org.apache.spark.sql.Row;

public final class SparkGroupingUtils {

    private SparkGroupingUtils() {
    }

    public static RelationalGroupedDataset groupBy(
            Dataset<Row> dataset,
            Column[] columns) {

        if (columns == null || columns.length == 0) {
            throw new IllegalArgumentException(
                    "At least one group column is required");
        }

        return dataset.groupBy(columns);
    }


    public static RelationalGroupedDataset rollup(
            Dataset<Row> dataset,
            Column[] columns) {

        if (columns == null || columns.length == 0) {
            throw new IllegalArgumentException(
                    "At least one rollup column is required");
        }

        return dataset.rollup(columns);
    }
}
