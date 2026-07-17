package dq.generator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


public final class DefaultValueGenerator {
    private DefaultValueGenerator() {
    }


    private static class Holder {
        private static final DefaultValueGenerator INSTANCE = new DefaultValueGenerator();
    }


    public static DefaultValueGenerator getInstance() {
        return Holder.INSTANCE;
    }


    public Object generate(Class<?> type) {
        if (type == String.class)
            return "dummy";

        if (type == Integer.class || type == int.class)
            return 1;

        if (type == Long.class || type == long.class)
            return 1L;

        if (type == Boolean.class || type == boolean.class)
            return true;

        if (type == Double.class || type == double.class)
            return 1D;

        if (type == Float.class || type == float.class)
            return 1F;

        if (type == BigDecimal.class)
            return BigDecimal.ONE;

        if (type == UUID.class)
            return UUID.randomUUID();

        if (type == LocalDate.class)
            return LocalDate.now();

        if (type == LocalDateTime.class)
            return LocalDateTime.now();

        if (type.isEnum()) {
            Object[] values = type.getEnumConstants();
            return values.length > 0
                    ? values[0]
                    : null;
        }
        return null;
    }
}