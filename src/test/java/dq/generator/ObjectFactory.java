package dq.generator;

public final class ObjectFactory {
    private ObjectFactory() {
    }

    private static class Holder {
        private static final ObjectFactory INSTANCE = new ObjectFactory();
    }

    public static ObjectFactory getInstance() {
        return Holder.INSTANCE;
    }

    public <T> T create(Class<T> clazz) {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();
            ReflectionUtils.populate(instance);
            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Unable to create object " + clazz.getName(), e);
        }
    }
}