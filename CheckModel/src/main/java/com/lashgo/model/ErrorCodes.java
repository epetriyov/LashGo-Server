package com.lashgo.model;

/**
 * Created by Eugene on 13.02.14.
 */
public final class ErrorCodes {
    public static final String USER_NOT_EXISTS = "user.not.exists";
    public static final String USER_ALREADY_EXISTS = "user.already_exists";
    public static final String UUID_IS_EMPTY = "uuid.is_empty";
    public static final String INVALID_CLIENT_TYPE = "client_type.invalid";
    public static final String SESSION_EXPIRED = "session.expired";
    public static final String REGISTRATION_ID_IS_EMPTY = "registration_id.empty";
    public static final String SESSION_IS_EMPTY = "session.empty";
    public static final String WRONG_SESSION = "session.not_exists";
    public static final String PHOTO_ALREADY_EXISTS = "photo.already_exists";
    public static final String PHOTO_READ_ERROR = "photo.read_error";
    public static final String PHOTO_WRITE_ERROR = "photo.write_error";
    public static final String UNSUPPORTED_SOCIAL = "social.unsupported";
    public static final String EMAIL_NEEDED = "email.is_needed";
    public static final String TEMP_USER_NOT_EXISTS = "user.temp.not_exists";
    public static final String INTERNAL_SERVER_ERROR = "server.internal_error";
    public static final String CHECK_ID_NULL = "check.empty_id";

    private ErrorCodes() {

    }
}
