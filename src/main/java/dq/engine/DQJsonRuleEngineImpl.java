package dq.engine;

import dq.entities.operation.Operation;
import dq.entities.rule.parser.JsonRuleParser;
import dq.model.Rule;
import dq.utils.UtilityProvider;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.List;

public class DQJsonRuleEngineImpl implements DQRuleEngine {
    @Override
    public Dataset<Row> runDQRules(Dataset<Row> inputDataset, String ruleJson) {
        JsonRuleParser jsonRuleParser = new JsonRuleParser(UtilityProvider.OBJECT_MAPPER);
        try {
            Operation operation = jsonRuleParser.parse(ruleJson);
            return inputDataset.agg(operation.evaluate());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Dataset<Row> runDQRules(Dataset<Row> inputDataset, List<Rule> rules) {
        // TODO(Not yet implemented)
        return null;
    }
}
