package deserializator;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class StringDeserializer implements Deserializer {

    // Class.forName(fullReadClassName).getConstructor(String.class, String.class, Integer.class).newInstance("1", "2", 3)

    private List<Class<?>> fieldObjectTypes = new ArrayList<>();
    private List<Object> fieldObjValues = new ArrayList<>();

    @Override
    public Object deserialize(String objectFile) {
        try(BufferedReader reader = new BufferedReader(new FileReader(objectFile))) {
            String fullReadClassName = reader.readLine();
            Constructor<?>[] declaredConstructors = Class.forName(fullReadClassName).getDeclaredConstructors();
            readFieldTypes(reader);
            int indexOfAppropriateConstructor = findAppropriateConstructorIndex(declaredConstructors);
            Object[] args = fieldObjValues.toArray();
            return Class.forName(fullReadClassName).getDeclaredConstructors()[indexOfAppropriateConstructor].newInstance(args);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred during object deserialization: ", e);
        }
    }

    private int findAppropriateConstructorIndex(Constructor<?>[] constructors) {
        int result = 0;
        for (int i = 0; i < constructors.length; i++) {
            if (Arrays.asList(constructors[i].getParameterTypes()).equals(fieldObjectTypes)) result = i;
        }
        return result;
    }

    private void readFieldTypes(BufferedReader reader) throws IOException {
        String fieldType;
        while ((fieldType = reader.readLine()) != null) {
            try {
                Integer value = Integer.parseInt(fieldType);
                fieldObjectTypes.add(Integer.class);
                fieldObjValues.add(value);
            } catch (Exception e) {
                fieldObjectTypes.add(String.class);
                fieldObjValues.add(fieldType);
            }
        }
    }
}
