package dq.entities.operation.composite;

import dq.entities.operation.Operation;
import org.apache.spark.sql.Column;

import java.util.StringJoiner;

public class OrOperation extends CompositeOperation {

    public OrOperation(Operation... operations) {
        super(operations);
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
        if (operations.isEmpty()) {
            return ""; // Or return null/throw exception based on your architectural needs
        }

        StringJoiner joiner = new StringJoiner(" OR ", "(", ")");
        for (Operation op : operations) {
            joiner.add(op.expression());
        }
        return joiner.toString();

    }


}
