package dq.engine;

import dq.entities.operation.Operation;
import dq.entities.operation.aggregate.AggregateOperation;
import dq.entities.operation.aggregate.AggregationContext;
import dq.entities.operation.aggregate.BaseAggregateOperation;
import dq.entities.operation.aggregate.GroupBySpec;
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
            if (operation instanceof BaseAggregateOperation) {
                GroupBySpec groupBySpec = jsonRuleParser.getGroupBySpec(ruleJson);
                AggregationContext aggregationContext = new AggregationContext(
                        groupBySpec,
                        List.of((AggregateOperation) operation)
                );
                return aggregationContext.execute(inputDataset);
            } else if (operation != null) {
                return inputDataset.filter(operation.evaluate());
            } else {
                throw new RuntimeException("Operation is null, unable to parse rule");
            }
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
