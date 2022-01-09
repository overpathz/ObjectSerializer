import deserialize.Deserializer;
import deserialize.StringDeserializer;
import model.MySerializable;
import model.NotSerializableObject;
import model.Person;
import serialize.Serializer;
import serialize.StringSerializer;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MainApplication {
    public static void main(String[] args) throws Exception {

        Person personObjToSerialize = new Person("Alex", "Surnames", 19);

        Serializer objSerializer = new StringSerializer();
        objSerializer.serialize(personObjToSerialize);

//        Deserializer objDeserializer = new StringDeserializer();
//        Person deserialize = (Person) objDeserializer.deserialize("Person.sobj");
//        System.out.println(deserialize);
    }
}
