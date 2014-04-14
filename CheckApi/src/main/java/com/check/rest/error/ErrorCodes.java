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
    public static final String REGISTRATION_ID_ALREADY_EXISTS = "registration_id.already_exists";
    public static final String SESSION_EXPIRED = "session.expired";
    public static final String REGISTRATION_ID_IS_EMPTY = "registration_id.empty";
    public static final String SESSION_IS_EMPTY = "session.empty";
    public static final String WRONG_CURRENT_CHECK_REQUEST = "check.current.false";

    private ErrorCodes() {

    }
}
