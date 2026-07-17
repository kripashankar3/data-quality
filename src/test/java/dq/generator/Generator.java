package dq.generator;


public final class Generator {
    private Generator() {
    }

    public static <T> ObjectBuilder<T> of(Class<T> clazz) {
        return new ObjectBuilder<>(clazz);
    }

}