package dq.generator;

import java.lang.reflect.Field;


public final class ReflectionUtils {
    private ReflectionUtils() {
    }

    public static <T> void populate(T instance) {
        Field[] fields = ReflectionCache.getInstance().getFields(instance.getClass());
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = DefaultValueGenerator.getInstance().generate(field.getType());

                if (value != null) {
                    field.set(instance, value);
                }
            } catch (Exception e) {
                throw new RuntimeException("Unable to populate field " + field.getName(), e);
            }
        }
    }
}