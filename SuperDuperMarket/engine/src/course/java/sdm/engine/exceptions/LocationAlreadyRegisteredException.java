package course.java.sdm.engine.exceptions;

public class LocationAlreadyRegisteredException extends RuntimeException{
    public LocationAlreadyRegisteredException() {
    }

    public LocationAlreadyRegisteredException(String message) {
        super(message);
    }
}
