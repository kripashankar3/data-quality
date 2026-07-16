package dq.model;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public record EvaluationResult(Dataset<Row> failedEvaluation, Dataset<Row> successEvaluation) {
}
