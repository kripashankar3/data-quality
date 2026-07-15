package dq.engine;

import dq.model.Rule;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.List;

public interface DQRuleEngine {

    Dataset<Row> runDQRules(Dataset<Row> inputDataset, String ruleJson);

    Dataset<Row> runDQRules(Dataset<Row> inputDataset, List<Rule> rules);
}
