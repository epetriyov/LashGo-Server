package main.java.com.check.rest.error;

/**
 * Created by Eugene on 19.03.14.
 */
public class UnautharizedException extends RuntimeException {
    public UnautharizedException(String errorCode) {
        super(errorCode);
    }
}
