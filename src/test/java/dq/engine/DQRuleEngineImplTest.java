package dq.engine;

import dq.BaseSetup;
import dq.generator.Generator;
import dq.model.EvaluationResult;
import dq.model.Rule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DQRuleEngineImplTest extends BaseSetup {
    private DQRuleEngineImpl dqRuleEngine = new DQRuleEngineImpl();
    private List<Rule> rules = Generator.of(Rule.class)
            .count(2)
            .customize((rule, index) -> {
                rule.setId("rule-" + index);
            })
            .buildList();

    @Test
    @DisplayName("Should evaluate given rule against input dataset")
    public void evaluate_given_rule_against_input_dataset() throws IOException {
        String json = Files.readString(Path.of(compositeRuleJsonPath));
        EvaluationResult result = dqRuleEngine.runDQRules(employeeDataset, json);
        assertNotNull(result.failedEvaluation());
        assertNotNull(result.successEvaluation());
        assertEquals(3, result.successEvaluation().count());
        assertEquals(7, result.failedEvaluation().count());
    }

    @Test
    @DisplayName("Should evaluate predicate rule against input dataset")
    public void evaluate_predicate_rule_against_input_dataset() throws IOException {
        String json = Files.readString(Path.of(predicateRuleJsonPath));
        EvaluationResult result = dqRuleEngine.runDQRules(employeeDataset, json);
        assertNotNull(result.failedEvaluation());
        assertNotNull(result.successEvaluation());
        assertEquals(2, result.successEvaluation().count());
        assertEquals(8, result.failedEvaluation().count());
    }

    @Test
    @DisplayName("Should evaluate given set of rules against input dataset")
    public void evaluate_rule_set_against_input_dataset() throws IOException {
        String predicateJson = Files.readString(Path.of(predicateRuleJsonPath));
        String compositeJson = Files.readString(Path.of(compositeRuleJsonPath));

        rules.get(0).setBody(predicateJson);
        rules.get(1).setBody(compositeJson);

        EvaluationResult result = dqRuleEngine.runDQRules(employeeDataset, rules);
        assertNotNull(result.failedEvaluation());
        assertNotNull(result.successEvaluation());
        assertEquals(1, result.successEvaluation().count());
        assertEquals(9, result.failedEvaluation().count());
    }
}