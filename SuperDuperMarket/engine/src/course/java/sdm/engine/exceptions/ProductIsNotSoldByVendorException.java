package course.java.sdm.engine.exceptions;

public class ProductIsNotSoldByVendorException extends RuntimeException{
    public ProductIsNotSoldByVendorException(String message) {
        super(message);
    }
}
