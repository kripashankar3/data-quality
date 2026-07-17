package dq.entities.operation.predicate;

import dq.BaseSetup;
import dq.entities.operation.Operation;
import dq.entities.operation.operand.ColumnOperand;
import dq.entities.operation.operand.LiteralOperand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PredicateOperationsTest extends BaseSetup {

    @Test
    @DisplayName("EqualOperation should match employees in Engineering department")
    void equalOperationMatchesEmployeesInEngineeringDepartment() {
        Operation operation = new EqualOperation(
                new ColumnOperand("department"),
                new LiteralOperand("Engineering"));

        assertEquals(5, countMatches(operation));
        assertEquals("(department == 'Engineering')", operation.expression());
    }

    @Test
    @DisplayName("NotEqualOperation should match employees outside Engineering department")
    void notEqualOperationMatchesEmployeesOutsideEngineeringDepartment() {
        Operation operation = new NotEqualOperation(
                new ColumnOperand("department"),
                new LiteralOperand("Engineering"));

        assertEquals(5, countMatches(operation));
        assertEquals("(department != 'Engineering')", operation.expression());
    }

    @Test
    @DisplayName("GreaterThanOperation should match employees older than 30")
    void greaterThanOperationMatchesEmployeesOlderThan30() {
        Operation operation = new GreaterThanOperation(
                new ColumnOperand("age"),
                new LiteralOperand(30));

        assertEquals(4, countMatches(operation));
        assertEquals("(age > 30)", operation.expression());
    }

    @Test
    @DisplayName("GreaterThanEqualOperation should match employees aged 29 or older")
    void greaterThanEqualOperationMatchesEmployeesAged29OrOlder() {
        Operation operation = new GreaterThanEqualOperation(
                new ColumnOperand("age"),
                new LiteralOperand(29));

        assertEquals(5, countMatches(operation));
        assertEquals("(age >= 29)", operation.expression());
    }

    @Test
    @DisplayName("LessThanOperation should match employees younger than 25")
    void lessThanOperationMatchesEmployeesYoungerThan25() {
        Operation operation = new LessThanOperation(
                new ColumnOperand("age"),
                new LiteralOperand(25));

        assertEquals(3, countMatches(operation));
        assertEquals("(age < 25)", operation.expression());
    }

    @Test
    @DisplayName("LessThanEqualOperation should match employees aged 25 or younger")
    void lessThanEqualOperationMatchesEmployeesAged25OrYounger() {
        Operation operation = new LessThanEqualOperation(
                new ColumnOperand("age"),
                new LiteralOperand(25));

        assertEquals(4, countMatches(operation));
        assertEquals("(age <= 25)", operation.expression());
    }

    @Test
    @DisplayName("InOperation should match employees from US or UK")
    void inOperationMatchesEmployeesFromUsOrUk() {
        Operation operation = new InOperation(
                new ColumnOperand("country"),
                new LiteralOperand(List.of("US", "UK"))
        );

        assertEquals(6, countMatches(operation));
    }

    @Test
    @DisplayName("ContainsOperation should match employees with a gmail address")
    void containsOperationMatchesEmployeesWithGmailAddress() {
        Operation operation = new ContainsOperation(
                new ColumnOperand("email"),
                new LiteralOperand("@gmail.com"));

        assertEquals(6, countMatches(operation));
    }

    @Test
    @DisplayName("StartsWithOperation should match employees whose name starts with J")
    void startsWithOperationMatchesEmployeesWhoseNameStartsWithJ() {
        Operation operation = new StartsWithOperation(
                new ColumnOperand("name"),
                new LiteralOperand("J"));

        assertEquals(2, countMatches(operation));
    }
}