import model.Person;
import serializator.Serializer;
import serializator.StringSerializer;

public class MainApplication {
    public static void main(String[] args) {

        Person personObjToSerialize = new Person("Alex", "Surnames", 19);

        Serializer objSerializer = new StringSerializer();
        objSerializer.serialize(personObjToSerialize);
    }
}
