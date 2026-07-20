package dq.generator;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public final class ReflectionCache {
    private ReflectionCache() {
    }

    private final ConcurrentMap<Class<?>, Field[]> cache = new ConcurrentHashMap<>();

    private static class Holder {
        private static final ReflectionCache INSTANCE = new ReflectionCache();
    }

    public static ReflectionCache getInstance() {
        return Holder.INSTANCE;
    }

    public Field[] getFields(Class<?> clazz) {
        return cache.computeIfAbsent(
                clazz,
                Class::getDeclaredFields
        );
    }
}