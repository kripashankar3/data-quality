package dq.engine;

import dq.entities.operation.Operation;
import dq.entities.operation.aggregate.BaseAggregateOperation;
import dq.entities.rule.parser.JsonRuleParser;
import dq.model.EvaluationResult;
import dq.model.Rule;
import dq.utils.UtilityProvider;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.functions;

import java.util.List;
import java.util.UUID;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.not;


public class DQRuleEngineImpl implements DQRuleEngine {
    @Override
    public EvaluationResult runDQRules(Dataset<Row> inputDataset, String ruleJson) {
        JsonRuleParser jsonRuleParser = new JsonRuleParser(UtilityProvider.OBJECT_MAPPER);
        try {
            Operation operation = jsonRuleParser.parse(ruleJson);
            if (operation instanceof BaseAggregateOperation) {
                /*
                 GroupBySpec groupBySpec = jsonRuleParser.getGroupBySpec(ruleJson);
                 AggregationContext aggregationContext = new AggregationContext(
                 groupBySpec,
                 List.of((AggregateOperation) operation)
                 );
                 return aggregationContext.execute(inputDataset);

                 Handle this as part of
                 https://github.com/kripashankar3/data-quality/issues/9
                 [Window Operation] | Add support for window functionality for aggregate operations
                 */
                return new EvaluationResult(null, null);
            } else if (operation != null) {
                String generatedColName = "rule_%s_evaluation_result"
                        .formatted(UUID.randomUUID().toString().substring(0, 7)); // to be replaced with rule id
                Dataset<Row> evaluatedDataset = inputDataset.withColumn(generatedColName, operation.evaluate());
                Dataset<Row> successEvaluation = evaluatedDataset.filter(col(generatedColName).equalTo(true));
                Dataset<Row> failedEvaluation = evaluatedDataset.filter(col(generatedColName).equalTo(false));
                successEvaluation.drop(generatedColName);
                failedEvaluation.drop(generatedColName);

                System.out.println("Success evaluation count: " + successEvaluation.count());
                System.out.println("Failed evaluation count: " + failedEvaluation.count());

                return new EvaluationResult(failedEvaluation, successEvaluation);
            } else {
                throw new RuntimeException("Operation is null, unable to parse rule");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public EvaluationResult runDQRules(Dataset<Row> inputDataset, List<Rule> rules) {
        try {
            // Assuming given rule are valid, since this responsibility lies with rule provider API/service
            JsonRuleParser jsonRuleParser = new JsonRuleParser(UtilityProvider.OBJECT_MAPPER);
            Dataset<Row> evaluatedDataset = inputDataset;
            for (Rule rule : rules) {
                Operation operation = jsonRuleParser.parse(rule.getBody());
                if (operation instanceof BaseAggregateOperation) {
                /*
                 GroupBySpec groupBySpec = jsonRuleParser.getGroupBySpec(ruleJson);
                 AggregationContext aggregationContext = new AggregationContext(
                 groupBySpec,
                 List.of((AggregateOperation) operation)
                 );
                 return aggregationContext.execute(inputDataset);

                 Handle this as part of
                 https://github.com/kripashankar3/data-quality/issues/9
                 [Window Operation] | Add support for window functionality for aggregate operations
                 */
                } else if (operation != null) {
                    String generatedColName = "rule_%s_evaluation_result".formatted(rule.getId());
                    evaluatedDataset = evaluatedDataset.withColumn(generatedColName, operation.evaluate());
                } else {
                    throw new RuntimeException("Operation is null, unable to parse rule");
                }
            }

            Column finalResult = functions.lit(true);

            for (Rule rule : rules) {
                String generatedColName = "rule_%s_evaluation_result".formatted(rule.getId());
                finalResult = finalResult.and(col(generatedColName));
                evaluatedDataset.drop(generatedColName);
            }

            Dataset<Row> successEvaluation = evaluatedDataset.filter(finalResult);
            Dataset<Row> failedEvaluation = evaluatedDataset.filter(not(finalResult));

            System.out.println("Success evaluation count: " + successEvaluation.count());
            System.out.println("Failed evaluation count: " + failedEvaluation.count());

            return new EvaluationResult(failedEvaluation, successEvaluation);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
