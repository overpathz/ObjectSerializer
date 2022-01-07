import model.Person;
import serializator.Serializer;
import serializator.StringSerializer;

import java.lang.reflect.InvocationTargetException;

public class MainApplication {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        Person personObjToSerialize = new Person("Alex", "Surnames", 19);

        Serializer objSerializer = new StringSerializer();
        objSerializer.serialize(personObjToSerialize);

//        Person person = (Person)
//                Class.forName("model.Person")
//                        .getConstructor(String.class, String.class, Integer.class)
//                        .newInstance("1", "2", 3);
//
//        System.out.println(person);
    }
}
