package dq.generator;

@FunctionalInterface
public interface Setter<T, V> {
    void apply(T target, V value);
}

