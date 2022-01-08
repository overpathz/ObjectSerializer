package serializator;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;

public class StringSerializer implements Serializer {

    private String objClassName;

    /*
        Serialization file will be contain:
        (must) 1 line - Class (to know the type of object I want to create(deserialization))
        other lines - fields
     */
    public void serialize(Object object) {
        this.objClassName = object.getClass().getSimpleName();
        Class<?> clazz = object.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        setAccessibleTrue(declaredFields);
        writeObjectFieldsToTheFile(declaredFields, object);
    }

    private void writeObjectFieldsToTheFile(Field[] declaredFields, Object object) {
        try (FileWriter writer = new FileWriter(objClassName + ".sobj")) {
            writer.write(object.getClass().getName() + '\n');
            for (Field field : declaredFields) {
                writer.write(field.get(object).toString() + '\n');
            }
        } catch (IOException | IllegalAccessException e) {
            throw new RuntimeException("Error occurred during object serialization: ", e);
        }
    }

    private void setAccessibleTrue(Field[] declaredFields) {
        Arrays.stream(declaredFields).forEach(x -> x.setAccessible(true));
    }
}
