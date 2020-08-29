package course.java.sdm.engine.exceptions;

public class LocationOutOfBoundsException extends RuntimeException {
    public LocationOutOfBoundsException(String errorMessage) {
        super(errorMessage);
    }

    public LocationOutOfBoundsException(String errorMessage, Throwable err) {
            super(errorMessage, err);
        }
}