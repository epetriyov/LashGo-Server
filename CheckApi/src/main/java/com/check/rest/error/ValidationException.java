package main.java.com.check.rest.error;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene
 * Date: 03.03.14
 * Time: 23:50
 * To change this template use File | Settings | File Templates.
 */
public class ValidationException extends RuntimeException {
    public ValidationException(String error) {
        super(error);
    }
}
