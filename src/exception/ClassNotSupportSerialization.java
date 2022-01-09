package exception;

public class ClassNotSupportSerialization extends RuntimeException {

    public ClassNotSupportSerialization(Object object) {
        super(object.getClass().getSimpleName() + " does not support serialization" );
    }
}
