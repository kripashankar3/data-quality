package dq.engine;

import dq.BaseSetup;
import dq.model.EvaluationResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DQJsonRuleEngineImplTest extends BaseSetup {
    private DQJsonRuleEngineImpl dqJsonRuleEngine = new DQJsonRuleEngineImpl();

    @Test
    @DisplayName("Should evaluate given rule against input dataset")
    public void evaluate_given_rule_against_input_dataset() throws IOException {
        String json = Files.readString(Path.of(compositeRuleJsonPath));
        EvaluationResult result = dqJsonRuleEngine.runDQRules(employeeDataset, json);
        assertNotNull(result.failedEvaluation());
        assertNotNull(result.successEvaluation());
        assertEquals(3, result.successEvaluation().count());
        assertEquals(7, result.failedEvaluation().count());
    }

    @Test
    @DisplayName("Should evaluate predicate rule against input dataset")
    public void evaluate_predicate_rule_against_input_dataset() throws IOException {
        String json = Files.readString(Path.of(predicateRuleJsonPath));
        EvaluationResult result = dqJsonRuleEngine.runDQRules(employeeDataset, json);
        assertNotNull(result.failedEvaluation());
        assertNotNull(result.successEvaluation());
        assertEquals(2, result.successEvaluation().count());
        assertEquals(8, result.failedEvaluation().count());
    }
}