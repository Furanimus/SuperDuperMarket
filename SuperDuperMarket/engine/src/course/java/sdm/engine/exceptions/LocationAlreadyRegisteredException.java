package course.java.sdm.engine.exceptions;

public class LocationAlreadyRegisteredException extends RuntimeException{
    public LocationAlreadyRegisteredException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public LocationAlreadyRegisteredException(String message) {
        super(message);
    }
}
