package serializator;

import model.Person;

import java.lang.reflect.Field;
import java.util.Arrays;

public class StringSerializer implements Serializer{
    /*
        Serialization file will be contain:
        1 line - Class (to know the type of object i want to create(deserialization))
        other lines - fields
     */
    public void serialize(Object object) {
        Class<?> personClass = (Class<Person>) object.getClass();
        Field[] declaredFields = personClass.getDeclaredFields();
        Arrays.stream(declaredFields).forEach(x->x.setAccessible(true));
        System.out.println("Done");
    }
}
