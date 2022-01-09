package deserialize;

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringDeserializer implements Deserializer {

    private List<Class<?>> fieldObjectTypes = new ArrayList<>();
    private List<Object> fieldObjValues = new ArrayList<>();

    @Override
    public Object deserialize(String objectFile) {
        try(BufferedReader reader = new BufferedReader(new FileReader(objectFile))) {
            String fullReadClassName = reader.readLine();
            Constructor<?>[] declaredConstructors = Class.forName(fullReadClassName).getDeclaredConstructors();
            readFieldTypes(reader);
            int indexOfAppropriateConstructor = findAppropriateConstructorIndex(declaredConstructors);
            Object[] argsToInstantiateObject = fieldObjValues.toArray();
            return Class.forName(fullReadClassName).getDeclaredConstructors()[indexOfAppropriateConstructor].newInstance(argsToInstantiateObject);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred during object deserialization: ", e);
        }
    }

    private int findAppropriateConstructorIndex(Constructor<?>[] constructors) {
        for (int i = 0; i < constructors.length; i++) {
            if (Arrays.asList(constructors[i].getParameterTypes()).equals(fieldObjectTypes)) return i;
        }
        return 0;
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
