import deserializator.Deserializer;
import deserializator.StringDeserializer;
import model.Person;
import serializator.Serializer;
import serializator.StringSerializer;

public class MainApplication {
    public static void main(String[] args) throws Exception {

        Person personObjToSerialize = new Person("Alex", "Surnames", 19);

        Serializer objSerializer = new StringSerializer();
        objSerializer.serialize(personObjToSerialize);

        Deserializer objDeserializer = new StringDeserializer();
        Person deserialize = (Person) objDeserializer.deserialize("Person.sobj");
        System.out.println(deserialize);
    }

    private static void some(Class<Person> personClass) {
        Person person = (personClass.cast(getObject()));
    }

    private static Object getObject() {
        return new Person("1", "2", 3);
    }
}
