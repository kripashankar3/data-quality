package dq.entities.operation.aggregate;

import dq.entities.BaseSetup;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NormalGroupBySpecTest extends BaseSetup {

    @Test
    @DisplayName("isEmpty should return true when no columns are provided")
    void isEmptyReturnsTrueWhenNoColumns() {
        NormalGroupBySpec spec = new NormalGroupBySpec(List.of());
        assertTrue(spec.isEmpty());
    }

    @Test
    @DisplayName("isEmpty should return false when columns are provided")
    void isEmptyReturnsFalseWhenColumnsProvided() {
        NormalGroupBySpec spec = new NormalGroupBySpec(List.of("department"));
        assertFalse(spec.isEmpty());
    }

    @Test
    @DisplayName("apply should group the dataset by the given columns")
    void applyGroupsDatasetByGivenColumns() {
        NormalGroupBySpec spec = new NormalGroupBySpec(List.of("department"));
        Dataset<Row> grouped = spec.apply(employeeDataset).count();
        assertEquals(3, grouped.count());
    }
}