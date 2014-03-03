package main.java.com.check.rest.error;

/**
 * Created by Eugene on 13.02.14.
 */
public final class ErrorCodes {
    public static final String USER_ALREADY_EXISTS = "user.already_exists";
    public static final String USER_NOT_FOUND = "user.not_found";
    public static final String UUID_IS_EMPTY = "uuid.is_empty";
    public static final String INCORRECT_DATA = "data.incorrect";
    public static final String INVALID_CLIENT_TYPE = "client_type.invalid";

    private ErrorCodes() {

    }
}