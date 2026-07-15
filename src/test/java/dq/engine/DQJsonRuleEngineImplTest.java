package dq.engine;

import dq.BaseSetup;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DQJsonRuleEngineImplTest extends BaseSetup {
    private DQJsonRuleEngineImpl dqJsonRuleEngine = new DQJsonRuleEngineImpl();

    @Test
    @DisplayName("Should evaluate given rule against input dataset")
    public void evaluate_given_rule_against_input_dataset() throws IOException {
        String json = Files.readString(Path.of(ruleJsonPath));
        Dataset<Row> result = dqJsonRuleEngine.runDQRules(employeeDataset, json);
        assertEquals(3, result.count());
        assertNull(result.filter("department == 'HR'").first().get(1));
        assertEquals(340000.0, result.filter("department == 'Engineering'").first().get(1));
        assertEquals(170000.0, result.filter("department == 'Finance'").first().get(1));
    }
}