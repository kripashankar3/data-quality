package dq.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class ObjectBuilder<T> {
    private final Class<T> clazz;
    private final int count;
    private final List<Consumer<T>> overrides;
    private final List<BiConsumer<T, Integer>> instanceCustomizers;

    public ObjectBuilder(Class<T> clazz) {
        this(
                clazz,
                1,
                List.of(),
                List.of()
        );
    }

    private ObjectBuilder(
            Class<T> clazz,
            int count,
            List<Consumer<T>> overrides,
            List<BiConsumer<T, Integer>> instanceCustomizers) {
        this.clazz = clazz;
        this.count = count;
        this.overrides = overrides;
        this.instanceCustomizers = instanceCustomizers;
    }


    /**
     * Override same value for every generated object
     */
    public <V> ObjectBuilder<T> with(Setter<T, V> setter, V value) {
        List<Consumer<T>> updated = new ArrayList<>(overrides);
        updated.add(obj -> setter.apply(obj, value));

        return new ObjectBuilder<>(
                clazz,
                count,
                List.copyOf(updated),
                instanceCustomizers
        );
    }


    /**
     * Customize each generated instance separately.
     * <p>
     * index starts from 0.
     */
    public ObjectBuilder<T> customize(BiConsumer<T, Integer> customizer) {
        List<BiConsumer<T, Integer>> updated = new ArrayList<>(instanceCustomizers);
        updated.add(customizer);

        return new ObjectBuilder<>(
                clazz,
                count,
                overrides,
                List.copyOf(updated)
        );
    }


    public ObjectBuilder<T> count(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Count must be greater than zero");
        }

        return new ObjectBuilder<>(
                clazz,
                count,
                overrides,
                instanceCustomizers
        );
    }

    public T build() {
        return build(0);
    }

    private T build(int index) {
        T instance = ObjectFactory.getInstance().create(clazz);

        /* Common overrides */
        overrides.forEach(override -> override.accept(instance));

        /* Instance-specific overrides */
        instanceCustomizers.forEach(customizer -> customizer.accept(instance, index));
        return instance;
    }

    public List<T> buildList() {
        List<T> result = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            result.add(build(i));
        }
        return result;
    }
}