package main.java.com.check.rest.error;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene
 * Date: 03.03.14
 * Time: 23:44
 * To change this template use File | Settings | File Templates.
 */
public class RegisterException extends Exception {
    public RegisterException(String error) {
        super(error);
    }
}
