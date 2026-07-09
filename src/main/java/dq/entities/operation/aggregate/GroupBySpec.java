package dq.entities.operation.aggregate;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.RelationalGroupedDataset;
import org.apache.spark.sql.Row;

public interface GroupBySpec {

    RelationalGroupedDataset apply(Dataset<Row> dataset);
}