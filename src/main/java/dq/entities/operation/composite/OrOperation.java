package dq.entities.operation.composite;

import dq.entities.operation.Operation;
import org.apache.spark.sql.Column;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class OrOperation implements Operation {

    private final List<Operation> operations;

    public OrOperation(Operation... operations) {
        this.operations = Arrays.asList(operations);
    }

    @Override
    public Column evaluate() {

        Column result = operations.getFirst().evaluate();

        for (int i = 1; i < operations.size(); i++) {
            result = result.or(operations.get(i).evaluate());
        }

        return result;
    }


    @Override
    public String expression() {
        if (operations == null || operations.isEmpty()) {
            return ""; // Or return null/throw exception based on your architectural needs
        }

        StringJoiner joiner = new StringJoiner(" OR ", "(", ")");
        for (Operation op : operations) {
            joiner.add(op.expression());
        }
        return joiner.toString();

    }


}
