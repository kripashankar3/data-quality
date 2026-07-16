package dq.engine;

import dq.model.EvaluationResult;
import dq.model.Rule;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.List;

public interface DQRuleEngine {

    EvaluationResult runDQRules(Dataset<Row> inputDataset, String ruleJson);

    EvaluationResult runDQRules(Dataset<Row> inputDataset, List<Rule> rules);
}
