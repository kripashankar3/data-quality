package dq.entities.operation.aggregate;

import dq.entities.operation.OperationTest;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RollUpGroupBySpecTest extends OperationTest {

    @Test
    @DisplayName("isEmpty should return true when no columns are provided")
    void isEmptyReturnsTrueWhenNoColumns() {
        RollUpGroupBySpec spec = new RollUpGroupBySpec(List.of());
        assertTrue(spec.isEmpty());
    }

    @Test
    @DisplayName("isEmpty should return false when columns are provided")
    void isEmptyReturnsFalseWhenColumnsProvided() {
        RollUpGroupBySpec spec = new RollUpGroupBySpec(List.of("department"));
        assertFalse(spec.isEmpty());
    }

    @Test
    @DisplayName("apply should roll up the dataset by the given columns, including a grand total row")
    void applyRollsUpDatasetIncludingGrandTotal() {
        RollUpGroupBySpec spec = new RollUpGroupBySpec(List.of("department"));
        Dataset<Row> grouped = spec.apply(employeeDataset).count();
        // 3 departments + 1 grand-total row that rollup adds with a null group key
        assertEquals(4, grouped.count());
    }
}