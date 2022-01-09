package serialize;

import exception.ClassNotSupportSerialization;
import model.MySerializable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;

import static java.util.stream.Collectors.*;

/**
 * Serialization file will be contain:<br>
 * (MUST) 1 line - [Type] Full class name (to know the type of object you want to deserialize)<br>
 * Other lines - fields<br>
 * <br>
 * Serialization can be performed only with classes which have no nested data structures (Collection)<br>
 * (It means serializer can not work recursively and works only with standard classes - String, Integer, Double, ..)<br>
 */
public class StringSerializer implements Serializer {

    private final ObjectInfo objectInfo;
    private static final String SERIALIZATION_FILE_EXTENSION = ".sobj";
    private static final String SERIALIZABLE_TYPE_NAME = MySerializable.class.getTypeName();

    {
        this.objectInfo = new ObjectInfo();
    }

    public void serialize(Object object) {
        if (checkSerializableInterfaceImplemented(object)) {
            init(object);
            processSerialization(object);
        } else {
            throw new ClassNotSupportSerialization(object);
        }
    }

    private void init(Object object) {
        objectInfo.setObject(object);
        objectInfo.setClassNameToNameSavingFile(object.getClass().getSimpleName());
        objectInfo.setFullClassName(object.getClass().getName());
    }

    private boolean checkSerializableInterfaceImplemented(Object object) {
        Set<String> annotatedInterfaces = Arrays.stream(object.getClass().getAnnotatedInterfaces())
                .map(x-> x.getType().getTypeName()).collect(toSet());

        return annotatedInterfaces.contains(SERIALIZABLE_TYPE_NAME);
    }

    private void processSerialization(Object object) {
        Field[] declaredFields = getDeclarationsFields(object);
        writeFieldsToFile(declaredFields, object);
    }

    private void writeFieldsToFile(Field[] declaredFields, Object object) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(objectInfo.getClassNameToNameSavingFile())))) {
            writer.println(objectInfo.getFullClassName());
            for (Field field : declaredFields) {
                writer.println(generateTypeAndValueString(field));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error occurred during object serialization: ", e);
        }
    }

    private String generateTypeAndValueString(Field field) throws IllegalAccessException {
        String fieldType = field.getType().getName();
        String fieldValue = field.get(objectInfo.getObject()).toString();
        return fieldType + ' ' + fieldValue;
    }

    private Field[] getDeclarationsFields(Object object) {
        Class<?> clazz = object.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        setAccessibleTrue(declaredFields);
        return declaredFields;
    }

    private void setAccessibleTrue(Field[] declaredFields) {
        Arrays.stream(declaredFields).forEach(x -> x.setAccessible(true));
    }

    static class ObjectInfo {
        private String classNameToNameSavingFile;
        private String fullClassName;
        private Object object;

        public Object getObject() {
            return object;
        }

        public void setObject(Object object) {
            this.object = object;
        }

        public String getClassNameToNameSavingFile() {
            return classNameToNameSavingFile;
        }

        public void setClassNameToNameSavingFile(String classNameToNameSavingFile) {
            this.classNameToNameSavingFile = classNameToNameSavingFile + SERIALIZATION_FILE_EXTENSION;
        }

        public String getFullClassName() {
            return fullClassName;
        }

        public void setFullClassName(String fullClassName) {
            this.fullClassName = fullClassName;
        }
    }
}
