package supplies;

import java.lang.reflect.Field;

public class ReflectionHelper {
    public static Object createInstance(String className) {
        try {
            return Class.forName(className).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Class " + className + " can't be reached.");
        }
    }

    public static void setFieldValue(Object object,
                                     String fieldName,
                                     String value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            if (field.getType().equals(String.class)) {
                field.set(object, value);
            } else if (field.getType().equals(int.class) || field.getType().equals(Integer.class)) {
                field.set(object, Integer.decode(value));
            }

            field.setAccessible(false);
        }
        catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Field " + fieldName + " can't be reached.");
        }
    }
}
