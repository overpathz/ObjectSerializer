package deserializator;

import java.io.File;

public interface Deserializer {
    Object deserialize(File objectFile);
}
