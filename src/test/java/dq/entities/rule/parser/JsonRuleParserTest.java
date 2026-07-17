package dq.entities.rule.parser;

import dq.BaseSetup;
import dq.entities.operation.Operation;
import dq.entities.operation.aggregate.SumOperation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class JsonRuleParserTest extends BaseSetup {

    @Test
    @DisplayName("Should parse rule dsl and return an operation")
    void shouldParseRuleDslAndReturnAnOperation() throws IOException {
        String json = Files.readString(Path.of(aggRuleJsonPath));
        JsonRuleParser jsonRuleParser = new JsonRuleParser(OBJECT_MAPPER);
        Operation operation = assertDoesNotThrow(
                () -> jsonRuleParser.parse(json)
        );
        assertNotNull(operation);
        assertInstanceOf(SumOperation.class, operation);
    }

}